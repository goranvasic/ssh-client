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

import lombok.Getter;

import java.util.concurrent.TimeUnit;

public class SshRetrySettings {

    @Getter
    private final int retries;

    @Getter
    private final int sleepDuration;

    @Getter
    private final TimeUnit timeUnit;

    public SshRetrySettings() {
        this.retries = 1;
        this.sleepDuration = 10;
        this.timeUnit = TimeUnit.SECONDS;
    }

    public SshRetrySettings(final int retries, final int sleepDuration, final TimeUnit timeUnit) {
        this.retries = (retries > 0) ? retries : 1;
        this.sleepDuration = sleepDuration;
        this.timeUnit = timeUnit;
    }
}
