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

public class Strings {

    public static final String CANNOT_READ_SSH_KEY_CONTENT = "Cannot read SSH key content.";
    public static final String EMPTY = "";
    public static final String ERROR_REDIRECTION = " 2>&1";
    public static final String EXEC = "exec";
    public static final String NO = "no";
    public static final String PASSWORD = "password";
    public static final String PREFERRED_AUTHENTICATIONS = "PreferredAuthentications";
    public static final String PUBLIC_KEY = "publickey";
    public static final String SESSION_IS_CLOSED = "Cannot execute SSH command, session is closed.";
    public static final String STRICT_HOST_KEY_CHECKING = "StrictHostKeyChecking";
    public static final String UNABLE_TO_CONNECT_VIA_SSH = "Unable to connect via SSH.";
    public static final String UNABLE_TO_EXECUTE_SSH_COMMAND = "Unable to execute SSH command.";

    private Strings() {
    }

}
