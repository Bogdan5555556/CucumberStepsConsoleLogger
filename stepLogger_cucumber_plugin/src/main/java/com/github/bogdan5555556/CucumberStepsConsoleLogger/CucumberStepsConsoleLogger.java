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
import java.util.Arrays;
import java.util.List;


public class CucumberStepsConsoleLogger implements Reporter, Formatter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CucumberStepsConsoleLogger.class);

    private List<Step> steps = new ArrayList<>();

    private Colours colour;

    private static String stepName = null;

    private static String fullStep = null;

    public CucumberStepsConsoleLogger() {
        String colour = System.getenv("CucumberStepsConsoleLoggerColour");
        if (colour == null) {
            this.colour = Colours.DEFAULT;
        } else this.colour = Colours.byName(colour.toUpperCase());
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
        stepName = null;
        fullStep = null;
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
        if (!(match instanceof StepDefinitionMatch)) {
            return;
        }
        String stepName = ((StepDefinitionMatch) match).getStepName();
        for (Step step1 : steps) {
            if (step1.getName().equals(stepName)) {
                step = step1;
            }
        }
        if (step != null) {
            CucumberStepsConsoleLogger.stepName = step.getName();
            StringBuilder stepLogName = new StringBuilder(step.getKeyword() + step.getName());
            if (step.getRows() != null) {
                List<Integer> maxElementInColumn = getMaxLengthCells(step.getRows());
                for (DataTableRow dataTableRow : step.getRows()) {
                    stepLogName.append("\n");
                    for (int i = 0; i < dataTableRow.getCells().size(); i++) {
                        String cellValue = dataTableRow.getCells().get(i);
                        char[] whiteSpaces = new char[maxElementInColumn.get(i) - cellValue.length()];
                        Arrays.fill(whiteSpaces, ' ');
                        stepLogName.append("\t|\t").append(cellValue).append(String.valueOf(whiteSpaces));
                    }
                    stepLogName.append("\t|\t");
                }
            }
            if (step.getDocString() != null) {
                stepLogName.append("\n");
                stepLogName.append("\'\'\'" + "\n").append(step.getDocString().getValue()).append("\n").append("\'\'\'");
            }
            String fullStep = stepLogName.toString();
            CucumberStepsConsoleLogger.fullStep = fullStep;
            LOGGER.info(buildStepExecutionMessage(fullStep));
        }
    }

    private List<Integer> getMaxLengthCells(List<DataTableRow> rows) {
        int cellsNumber = rows.get(0).getCells().size();
        List<Integer> maxElementInColumn = new ArrayList<>();
        for (int i = 0; i < cellsNumber; i++) {
            int maxValue = 0;
            for (DataTableRow row : rows) {
                int length = row.getCells().get(i).length();
                if (length > maxValue) {
                    maxElementInColumn.add(i, length);
                    maxValue = length;
                }
            }
        }
        return maxElementInColumn;
    }

    private String buildStepExecutionMessage(String stepLogName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n\n---------- Test Step Execution ----------\n");
        colour.colorize(stringBuilder, stepLogName);
        stringBuilder.append("\n---------- Test Step Execution ----------\n");
        return stringBuilder.toString();
    }

    @Override
    public void embedding(String mimeType, byte[] data) {
    }

    @Override
    public void write(String text) {
    }

    public static String getStepName() {
        return stepName;
    }

    public static String getFullStep() {
        return fullStep;
    }
}
