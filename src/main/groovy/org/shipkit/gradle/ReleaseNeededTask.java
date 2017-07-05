package org.shipkit.gradle;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.shipkit.internal.gradle.release.tasks.ReleaseNeeded;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Decides if the release is needed.
 * It is necessary to avoid making releases in certain scenarios like when we are building pull requests.
 * <p>
 * The release is <strong>not needed</strong> when any of below is true:
 *  - the env variable 'SKIP_RELEASE' is present
 *  - the commit message, loaded from 'TRAVIS_COMMIT_MESSAGE' env variable contains '[ci skip-release]' keyword
 *  - the env variable 'TRAVIS_PULL_REQUEST' is not empty, not an empty String and and not 'false'
 *  - the current Git branch does not match release-eligibility regex ({@link #getReleasableBranchRegex()}.
 *  - binaries have not changes since the previous release
 */
public class ReleaseNeededTask extends DefaultTask {

    private String branch;
    private String releasableBranchRegex;
    private String commitMessage;
    private boolean pullRequest;
    private boolean explosive;
    private List<File> comparisonResults = new LinkedList<File>();

    /**
     * The branch we currently operate on
     */
    public String getBranch() {
        return branch;
    }

    /**
     * See {@link #getBranch()}
     */
    public void setBranch(String branch) {
        this.branch = branch;
    }

    /**
     * Regex to be used to identify branches that are entitled to be released, for example "master|release/.+"
     */
    public String getReleasableBranchRegex() {
        return releasableBranchRegex;
    }

    /**
     * See {@link #getReleasableBranchRegex()}
     */
    public void setReleasableBranchRegex(String releasableBranchRegex) {
        this.releasableBranchRegex = releasableBranchRegex;
    }

    /**
     * Commit message the build job was triggered with
     */
    public String getCommitMessage() {
        return commitMessage;
    }

    /**
     * See {@link #getCommitMessage()}
     */
    public void setCommitMessage(String commitMessage) {
        this.commitMessage = commitMessage;
    }

    /**
     * Pull request this job is building
     */
    public boolean isPullRequest() {
        return pullRequest;
    }

    /**
     * See {@link #isPullRequest()}
     */
    public void setPullRequest(boolean pullRequest) {
        this.pullRequest = pullRequest;
    }

    /**
     * If the exception should be thrown if the release is not needed.
     */
    public boolean isExplosive() {
        return explosive;
    }

    /**
     * See {@link #isExplosive()}
     */
    public ReleaseNeededTask setExplosive(boolean explosive) {
        this.explosive = explosive;
        return this;
    }

    public List<File> getComparisonResults() {
        return comparisonResults;
    }

    public void setComparisonResults(List<File> comparisonResults) {
        this.comparisonResults = comparisonResults;
    }

    @TaskAction public boolean releaseNeeded() {
        return new ReleaseNeeded().releaseNeeded(this);
    }
}
