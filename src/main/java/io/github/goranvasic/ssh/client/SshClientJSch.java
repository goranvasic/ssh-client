/*******************************************************************************
 * Copyright 2022 Goran Vasic
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package io.github.goranvasic.ssh.client;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import io.github.goranvasic.ssh.credentials.SshCredentials;
import io.github.goranvasic.ssh.exceptions.SshClientException;
import io.github.goranvasic.ssh.utils.SshCommandExecutor;
import io.github.goranvasic.ssh.utils.SshHost;
import io.github.goranvasic.ssh.utils.SshSession;
import io.github.goranvasic.ssh.utils.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SshClientJSch implements SshClient {

    private static final JSch jSch = new JSch();
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private Session session;

    @Override
    public void connect(final SshHost host, final SshCredentials credentials) throws SshClientException {
        connect(host, credentials, new SshRetrySettings());
    }

    @Override
    public void connect(final SshHost host, final SshCredentials credentials, final SshRetrySettings retrySettings)
        throws SshClientException {
        int tryNum = 1;
        do {
            try {
                final SshSession sshSession = new SshSession(jSch);
                session = sshSession.getSession(host, credentials);
                return;
            } catch (JSchException e) {
                log.warn(String.format("Try %d/%d failed with %s", tryNum, retrySettings.getRetries(), e));
                logStackTrace(e);
                if (tryNum == retrySettings.getRetries()) {
                    session = null;
                    log.error(Strings.UNABLE_TO_CONNECT_VIA_SSH, e);
                    throw new SshClientException(Strings.UNABLE_TO_CONNECT_VIA_SSH, e);
                }
                log.info(String.format("Retrying in %d %s...", retrySettings.getSleepDuration(),
                    retrySettings.getTimeUnit()
                        .toString()
                        .toLowerCase()));
                sleep(retrySettings.getSleepDuration(), retrySettings.getTimeUnit());
                ++tryNum;
            }
        } while (tryNum <= retrySettings.getRetries());
    }

    @Override
    public void disconnect() {
        if (null != session) {
            session.disconnect();
        }
    }

    @Override
    public SshResponse execute(final String command) throws SshClientException {
        if (null != session) {
            try {
                SshCommandExecutor executor = new SshCommandExecutor(session);
                return executor.executeCommand(command);
            } catch (JSchException | IOException exception) {
                throw new SshClientException(Strings.UNABLE_TO_EXECUTE_SSH_COMMAND, exception);
            }
        }
        throw new SshClientException(Strings.SESSION_IS_CLOSED);
    }

    private void logStackTrace(final JSchException e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        for (StackTraceElement element : stackTrace) {
            if (null != element && element.toString()
                .contains(this.getClass()
                    .getPackage()
                    .getName())) {
                String stackTraceElement = element.toString();
                log.warn(stackTraceElement);
            }
        }
    }

    private void sleep(final long duration, final TimeUnit timeUnit) {
        try {
            Thread.sleep(timeUnit.toMillis(duration));
        } catch (InterruptedException interruptedException) {
            Thread.currentThread()
                .interrupt();
        }
    }
}
