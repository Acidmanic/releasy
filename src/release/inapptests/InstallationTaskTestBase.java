/*
 * Copyright (C) 2018 Mani Moayedi (acidmanic.moayedi@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package release.inapptests;

import com.acidmanic.installation.InstallerBase;
import com.acidmanic.installation.models.DeploymentMetadata;
import com.acidmanic.installation.tasks.InstallationTask;
import java.util.List;

/**
 *
 * @author Mani Moayedi (acidmanic.moayedi@gmail.com)
 */
public class InstallationTaskTestBase {

    private class Feeder extends InstallationTask<Object, Object> {

        private final Object sutInput;

        public Feeder(Object sutInput) {
            this.sutInput = sutInput;
        }

        @Override
        protected boolean onWindows(Object input) {
            this.result = sutInput;
            return true;
        }

        @Override
        protected boolean onUnix(Object input) {
            this.result = sutInput;
            return true;
        }

        @Override
        public boolean isIgnorable() {
            return true;
        }

    }

    private class TestTask extends InstallerBase {

        private final InstallationTask task;
        private final Object sutInput;
        private List<InstallationTask> hijacked;

        public TestTask(InstallationTask task, Object sutInput) {
            this.task = task;
            this.sutInput = sutInput;
        }

        @Override
        protected void setupMetaData(DeploymentMetadata metadata) {
            metadata.setOrganizationName("Acidmanic");
            metadata.setProductName("test");
        }

        @Override
        protected void introduceTasks(List<InstallationTask> tasks) {
            hijacked = tasks;
        }

        @Override
        public void install() {
            hijacked.clear();
            hijacked.add(new Feeder(this.sutInput));
            hijacked.add(task);
            super.install();
        }

    }

    protected boolean runExecuteMethod(InstallationTask task, Object input) {
        TestTask tester = new TestTask(task, input);
        tester.install();
        List<Boolean> results = tester.getResults();
        return results.size() > 1 && results.get(1);
    }
}
