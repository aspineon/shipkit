package org.mockito.release.internal.gradle;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.mockito.release.notes.format.ReleaseNotesFormatters;
import org.mockito.release.notes.generator.ReleaseNotesGenerator;
import org.mockito.release.notes.generator.ReleaseNotesGenerators;
import org.mockito.release.notes.model.ReleaseNotesData;
import org.mockito.release.notes.util.IOUtil;

import java.io.File;
import java.util.Collection;

public class NotableReleaseNotesGeneratorTask extends DefaultTask {

    //TODO documentation
    private final NotesGeneration notesGeneration = new NotesGeneration();

    public NotesGeneration getNotesGeneration() {
        return notesGeneration;
    }

    @TaskAction public void generateReleaseNotes() {
        ReleaseNotesGenerator generator = ReleaseNotesGenerators.releaseNotesGenerator(
                notesGeneration.gitWorkingDir, notesGeneration.gitHubRepository, notesGeneration.gitHubReadOnlyAuthToken);
        //TODO release notes generation should produce JSON data that we can keep between the builds in the cache
        //then, the markdown generation logic would parse the JSON and produce human readable notes
        Collection<ReleaseNotesData> releaseNotes = generator.generateReleaseNotesData(
                notesGeneration.headVersion, notesGeneration.targetVersions, notesGeneration.tagPrefix, notesGeneration.gitHubLabels, notesGeneration.onlyPullRequests);
        String notes = ReleaseNotesFormatters.notableFormatter(
                notesGeneration.introductionText, notesGeneration.detailedReleaseNotesLink, notesGeneration.vcsCommitsLinkTemplate)
                .formatReleaseNotes(releaseNotes);
        IOUtil.writeFile(notesGeneration.outputFile, notes);
    }

    //TODO expose as public API
    public class NotesGeneration {
        private File gitWorkingDir;
        private String gitHubRepository;
        private String gitHubReadOnlyAuthToken;
        private Collection<String> targetVersions;
        private String tagPrefix;
        private Collection<String> gitHubLabels;
        private File outputFile;
        private boolean onlyPullRequests;
        private String introductionText;
        private String detailedReleaseNotesLink;
        private String vcsCommitsLinkTemplate;
        private String headVersion;

        public File getGitWorkingDir() {
            return gitWorkingDir;
        }

        public void setGitWorkingDir(File gitWorkingDir) {
            this.gitWorkingDir = gitWorkingDir;
        }

        public String getGitHubRepository() {
            return gitHubRepository;
        }

        public void setGitHubRepository(String gitHubRepository) {
            this.gitHubRepository = gitHubRepository;
        }

        public String getGitHubReadOnlyAuthToken() {
            return gitHubReadOnlyAuthToken;
        }

        public void setGitHubReadOnlyAuthToken(String gitHubReadOnlyAuthToken) {
            this.gitHubReadOnlyAuthToken = gitHubReadOnlyAuthToken;
        }

        public Collection<String> getTargetVersions() {
            return targetVersions;
        }

        public void setTargetVersions(Collection<String> targetVersions) {
            this.targetVersions = targetVersions;
        }

        public String getTagPrefix() {
            return tagPrefix;
        }

        public void setTagPrefix(String tagPrefix) {
            this.tagPrefix = tagPrefix;
        }

        public Collection<String> getGitHubLabels() {
            return gitHubLabels;
        }

        public void setGitHubLabels(Collection<String> gitHubLabels) {
            this.gitHubLabels = gitHubLabels;
        }

        public File getOutputFile() {
            return outputFile;
        }

        public void setOutputFile(File outputFile) {
            this.outputFile = outputFile;
        }

        public boolean isOnlyPullRequests() {
            return onlyPullRequests;
        }

        public void setOnlyPullRequests(boolean onlyPullRequests) {
            this.onlyPullRequests = onlyPullRequests;
        }

        public String getIntroductionText() {
            return introductionText;
        }

        public void setIntroductionText(String introductionText) {
            this.introductionText = introductionText;
        }

        public String getDetailedReleaseNotesLink() {
            return detailedReleaseNotesLink;
        }

        public void setDetailedReleaseNotesLink(String detailedReleaseNotesLink) {
            this.detailedReleaseNotesLink = detailedReleaseNotesLink;
        }

        public String getVcsCommitsLinkTemplate() {
            return vcsCommitsLinkTemplate;
        }

        public void setVcsCommitsLinkTemplate(String vcsCommitsLinkTemplate) {
            this.vcsCommitsLinkTemplate = vcsCommitsLinkTemplate;
        }

        public String getHeadVersion() {
            return headVersion;
        }

        public void setHeadVersion(String headVersion) {
            this.headVersion = headVersion;
        }
    }
}
