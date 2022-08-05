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

package io.github.goranvasic.ssh.utils;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import io.github.goranvasic.ssh.client.SshResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SshCommandExecutor {

    private final Session session;

    public SshCommandExecutor(final Session session) {
        this.session = session;
    }

    public SshResponse executeCommand(final String command) throws JSchException, IOException {
        ChannelExec channelExec = (ChannelExec) session.openChannel(Strings.EXEC);
        InputStream commandOutput = channelExec.getInputStream();
        channelExec.setCommand(command + Strings.ERROR_REDIRECTION);
        channelExec.connect();
        StringBuilder result = new StringBuilder();
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(commandOutput));
        boolean isFirstLine = true;
        while (null != (line = reader.readLine())) {
            if (isFirstLine) {
                isFirstLine = false;
            } else {
                result.append("\n");
            }
            result.append(line);
        }
        String commandResult = result.toString();
        channelExec.disconnect();
        return new SshResponse(commandResult);
    }
}
