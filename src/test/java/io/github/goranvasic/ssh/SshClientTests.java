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

package io.github.goranvasic.ssh;

import io.github.goranvasic.ssh.client.SshClient;
import io.github.goranvasic.ssh.client.SshClientJSch;
import io.github.goranvasic.ssh.client.SshResponse;
import io.github.goranvasic.ssh.credentials.SshCredentials;
import io.github.goranvasic.ssh.exceptions.SshClientException;
import io.github.goranvasic.ssh.utils.SshHost;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SshClientTests {
    @Test
    @DisplayName("SSH Client Example")
    void sshClientExample() {
        SshClient sshClient = new SshClientJSch();
        SshHost host = new SshHost("127.0.0.1", 2222);
        SshCredentials credentials = new SshCredentials("root", "P@$$w0rd");
        try {
            sshClient.connect(host, credentials);
            SshResponse sshResponse = sshClient.execute("ls -l");
            String result = sshResponse.getMessage();
            assertNotSame("", result);
            sshClient.disconnect();
        } catch (SshClientException e) {
            throw new RuntimeException(e);
        }
    }
}
