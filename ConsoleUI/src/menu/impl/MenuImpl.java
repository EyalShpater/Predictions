package menu.impl;

import impl.*;
import execution.simulation.api.PredictionsLogic;
import execution.simulation.impl.PredictionsLogicImpl;
import menu.api.Menu;
import menu.api.MenuOptions;
import menu.helper.PrintToScreen;
import menu.helper.TypesScanner;

import java.util.*;
import java.util.stream.Collectors;

public class MenuImpl implements Menu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final TypesScanner typeScanner = new TypesScanner();
    private static final PrintToScreen printer = new PrintToScreen();

    private static final int EXIT = 5;
    private static final int YES = 1;
    private static final int NO = 2;
    private static final int AMOUNT_VIEW = 1;
    public static final int HISTOGRAM_VIEW = 2;

    private PredictionsLogic engine = new PredictionsLogicImpl();

    @Override
    public void show() {
        int choice = 0;

        loadXml();

        //engine.hardCodeWorldInit();
        while (choice != EXIT) {
            printer.displayMenu();
            choice = typeScanner.getIntFromUserInRange(1, MenuOptions.values().length);

            switch (MenuOptions.fromInt(choice)) {
                case LOAD_FILE:
                    loadXml();
                    break;
                case SHOW_SIMULATION_DETAILS:
                    showSimulationDetails();
                    break;
                case RUN_SIMULATION:
                    runSimulation();
                    break;
                case SHOW_PREVIOUS_SIMULATIONS:
                    showPreviousSimulation();
                    break;
                case EXIT:
                    // save to file (bonus)
                    break;
            }
        }
    }

    private void loadXml(){
        try{
            loadFileFromUser();
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            show();
        }
    }

    private void loadFileFromUser() {
        String filePath;
        boolean isLegalPath = false;

        printer.printLoadFileMenu();
        while (!isLegalPath) {
            try {
                filePath = scanner.nextLine();
                engine.loadXML(filePath);
                isLegalPath = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("File loaded successfully!");
    }

    private void showSimulationDetails() {
        WorldDTO details = engine.getLoadedSimulationDetails();

        printer.printWorldDTO(details);
    }

    private void runSimulation() {
        List<PropertyDefinitionDTO> updatedEnvironmentVariables = getEnvironmentVariablesFromUser();

        engine.runNewSimulation(updatedEnvironmentVariables);
    }

    private void showPreviousSimulation() {
        List<SimulationDTO> allSimulations = engine.getPreviousSimulationsAsDTO();

        if (allSimulations != null && !allSimulations.isEmpty()) {
            int simulationIndex, viewOption;

            printer.printTitle("Get Previous Simulations Data", '~', '-');
            System.out.println("Please choose a simulation");
            printer.showAllSimulationsDTO(allSimulations);
            simulationIndex = typeScanner.getIntFromUserInRange(1, allSimulations.size()) - 1;
            printer.printSimulationDetailViewOptions();
            viewOption = typeScanner.getIntFromUserInRange(1, 2);
            showPreviousSimulationDetails(allSimulations.get(simulationIndex).getSerialNumber(), viewOption);
        } else {
            System.out.println("No simulations to view");
        }
    }

    private void showPreviousSimulationDetails(int simulationSerialNumber, int viewOption) {
        SimulationDataDTO data;

        switch (viewOption) {
            case AMOUNT_VIEW:
                printer.showAmountViewSimulationDTO(simulationSerialNumber, engine);
                break;
            case HISTOGRAM_VIEW:
                data = getInputForHistogramView(simulationSerialNumber);
                printer.showHistogramViewSimulationDTO(data);
                break;
        }
    }

    private SimulationDataDTO getInputForHistogramView(int serialNumber) {
        SimulationDataDTO data;
        String chosenEntityName, chosenPropertyName;

        chosenEntityName = getEntityNameFromUser();
        chosenPropertyName = getPropertyNameByUser(chosenEntityName);
        data = engine.getSimulationData(serialNumber, chosenEntityName, chosenPropertyName);

        return data;
    }

    private String getEntityNameFromUser() {
        int entityIndex, count = 1;
        List<String> entitiesNames = engine
                        .getLoadedSimulationDetails()
                        .getEntities()
                        .stream().map(EntityDefinitionDTO::getName)
                        .collect(Collectors.toList());

        for (String name : entitiesNames) {
            System.out.println(count + ". " + name);
            count++;
        }

        System.out.println("Please choose an entity:");
        entityIndex = typeScanner.getIntFromUserInRange(1, entitiesNames.size());

        return engine
                .getLoadedSimulationDetails()
                .getEntities()
                .get(entityIndex - 1)
                .getName();
    }

    private String getPropertyNameByUser(String entityName) {
        List<PropertyDefinitionDTO> properties;
        int propertyIndex;

        properties = engine.getEntityPropertiesByEntityName(entityName);
        printer.printPropertyDefinitionDTOList(properties);
        System.out.println("Please choose property");
        propertyIndex = typeScanner.getIntFromUserInRange(1, properties.size()) - 1;

        return engine
                .getEntityPropertiesByEntityName(entityName)
                .get(propertyIndex)
                .getName();
    }

    private List<PropertyDefinitionDTO> getEnvironmentVariablesFromUser() {
        List<PropertyDefinitionDTO> environmentVariables = engine.getEnvironmentVariablesToSet();
        int choice = -1;

        printer.printTitle("Set Environment Variables", '~', '-');
        while (choice != 0) {
            System.out.println("Please enter the number of variable you want to set, or 0 to finish the setup");
            printer.showEnvironmentVariablesChooseMenu(environmentVariables);
            choice = typeScanner.getIntFromUserInRange(0, environmentVariables.size());

            if (choice != 0) {
                PropertyDefinitionDTO toUpdate = environmentVariables.get(choice - 1);
                environmentVariables.set(choice - 1, initEnvironmentVariableDTOFromUserInput(toUpdate));
            }
        }

        return environmentVariables;
    }

    private PropertyDefinitionDTO initEnvironmentVariableDTOFromUserInput(PropertyDefinitionDTO variableDTO) {
        Object value;

        System.out.println("Please enter a value");
        switch (variableDTO.getType()) {
            case "INT":
                value = variableDTO.getFrom() == null ?
                        typeScanner.getIntFromUser() :
                        typeScanner.getIntFromUserInRange(variableDTO.getFrom().intValue(), variableDTO.getTo().intValue());
                break;
            case "DOUBLE":
                value = variableDTO.getFrom() == null ?
                        typeScanner.getDoubleFromUser() :
                        typeScanner.getDoubleFromUserInRange(variableDTO.getFrom(), variableDTO.getTo());
                break;
            case "BOOLEAN":
                value = typeScanner.getBooleanFromUser();
                break;
            default:
                value = scanner.nextLine();
        }

        return new PropertyDefinitionDTO(
                variableDTO.getName(),
                variableDTO.getType(),
                variableDTO.getFrom(),
                variableDTO.getTo(),
                false,
                value
        );
    }

    private void askUserIfHeWantToUpdateTheVariable(String name) {
        System.out.println("Do you want to update " + name + "'s value?");
        System.out.println(YES + ". Yes");
        System.out.println(NO + ". No");
    }
}
