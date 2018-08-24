import hudson.triggers.TimerTrigger;
import javaposse.jobdsl.plugin.*;
import jenkins.model.Jenkins;
import hudson.model.FreeStyleProject

jenkins = Jenkins.instance;

jobName = "PSE JOBS seeder";
timerTrigger = new TimerTrigger("H/15 * * * *");
dslBuilder = new ExecuteDslScripts(scriptText: '''
job('PSE-GitCheock-outifchanges') {
    scm {
        git('https://github.com/schugale13/PSE-Project2017.git')
    }
    triggers {
        scm('H/15 * * * *')
    }
}
''');
dslProject = new FreeStyleProject(jenkins, jobName);
dslProject.addTrigger(timerTrigger);
dslProject.createTransientActions();
dslProject.getPublishersList().add(dslBuilder);
jenkins.add(dslProject, jobName);
timerTrigger.start(dslProject, true);




