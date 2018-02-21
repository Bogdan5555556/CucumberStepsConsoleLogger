package com.github.bogdan5555556.CucumberStepsConsoleLogger;

import cucumber.runtime.StepDefinitionMatch;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.Background;
import gherkin.formatter.model.DataTableRow;
import gherkin.formatter.model.Examples;
import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Match;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.Scenario;
import gherkin.formatter.model.ScenarioOutline;
import gherkin.formatter.model.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class CucumberStepsConsoleLogger implements Reporter, Formatter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CucumberStepsConsoleLogger.class);

    private static final String ANSI_MAGENTA = "\u001B[35m";
    private static final String ANSI_RESET = "\u001B[0m";

    private List<Step> steps = new ArrayList<>();

    private Colours colour;

    CucumberStepsConsoleLogger(){
        String colour = System.getenv("CucumberStepsConsoleLoggerColour");
        if(colour == null){
            this.colour = Colours.DEFAULT;
        }
        else this.colour = Colours.byName(colour.toUpperCase());
    }

    @Override
    public void syntaxError(String state, String event, List<String> legalEvents, String uri, Integer line) {
    }

    @Override
    public void uri(String uri) {
    }

    @Override
    public void feature(Feature feature) {
    }

    @Override
    public void scenarioOutline(ScenarioOutline scenarioOutline) {

    }

    @Override
    public void examples(Examples examples) {

    }

    @Override
    public void startOfScenarioLifeCycle(Scenario scenario) {
    }

    @Override
    public void background(Background background) {

    }

    @Override
    public void scenario(Scenario scenario) {

    }

    @Override
    public void step(Step step) {
        steps.add(step);
    }

    @Override
    public void endOfScenarioLifeCycle(Scenario scenario) {

    }

    @Override
    public void done() {

    }

    @Override
    public void close() {

    }

    @Override
    public void eof() {

    }

    @Override
    public void before(Match match, Result result) {

    }

    @Override
    public void result(Result result) {

    }

    @Override
    public void after(Match match, Result result) {

    }

    @Override
    public void match(Match match) {
        Step step = null;
        String stepName = ((StepDefinitionMatch) match).getStepName();
        for (Step step1 : steps) {
            if (step1.getName().equals(stepName)) {
                step = step1;
            }
        }

        String stepLogName = step.getKeyword() + " " + step.getName();
        if (step.getRows() != null) {
            for (DataTableRow dataTableRow : step.getRows()) {
                stepLogName = stepLogName + "\n";
                for (String cell : dataTableRow.getCells()) {
                    stepLogName = stepLogName + "\t|\t" + cell;
                }
                stepLogName = stepLogName + "\t|\t";
            }
        }
        if (step.getDocString() != null) {
            stepLogName = stepLogName + "\n";
            stepLogName = stepLogName + "\'\'\'" + "\n" + step.getDocString().getValue() + "\n" + "\'\'\'";
        }
        LOGGER.info(buildStepExecutionMessageBuild(stepLogName));
    }

    private String buildStepExecutionMessageBuild(String stepLogName){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n\n---------- Test Step Execution ----------\n");
        colour.appendTo(stringBuilder);
        stringBuilder.append(stepLogName);
        Colours.RESET.appendTo(stringBuilder);
        stringBuilder.append("\n---------- Test Step Execution ----------\n");
        return stringBuilder.toString();
    }

    @Override
    public void embedding(String mimeType, byte[] data) {
    }

    @Override
    public void write(String text) {
    }
}
