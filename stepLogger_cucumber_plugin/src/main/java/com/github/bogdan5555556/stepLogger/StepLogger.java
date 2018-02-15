package com.github.bogdan5555556.stepLogger;

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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class StepLogger implements Reporter, Formatter {

    private static final Logger LOGGER = LoggerFactory.getLogger(StepLogger.class);

    private static final String ANSI_MAGENTA = "\u001B[35m";
    private static final String ANSI_RESET = "\u001B[0m";

    private List<Step> steps = new ArrayList<>();
    String stepLocation;
    private List<Method> beforeFeature = new ArrayList<>();

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
        LOGGER.info("\n\n---------- Test Step Execution ----------\n" + ANSI_MAGENTA + stepLogName + ANSI_RESET + "\n---------- Test Step Execution ----------\n");
    }

    @Override
    public void embedding(String mimeType, byte[] data) {
    }

    @Override
    public void write(String text) {
    }
}
