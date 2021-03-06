package org.shipkit.gradle.plugin;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskAction;
import org.shipkit.internal.gradle.plugin.tasks.PluginValidator;

/**
 * This task validates plugin properties files.
 * The following constraints are validated:
 * <ul>
 *     <li>the naming convention of a plugin:</li>
 *     acceptable names are calculated based on the plugin id and the implementation class has to match one of them, e.g:
 *     <br>
 *     "org.shipkit.bintray" -> "OrgShipkitBintrayPlugin", "ShipkitBintrayPlugin", "BintrayPlugin"
 *     <li>the implementation class specified in a plugin properties file exists</li>
 * </ul>
 */
public class PluginValidatorTask extends DefaultTask {

    @Input
    private SourceSet sourceSet;

    @TaskAction
    public void validate() {
        new PluginValidator().validate(sourceSet);
    }

    /**
     * The {@link SourceSet} used for plugin validation.
     */
    public SourceSet getSourceSet() {
        return sourceSet;
    }

    /**
     * See {@link #getSourceSet()}
     */
    public void setSourceSet(SourceSet sourceSet) {
        this.sourceSet = sourceSet;
    }
}
