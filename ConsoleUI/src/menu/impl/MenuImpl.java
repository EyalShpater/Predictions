package menu.impl;

import impl.*;
import execution.simulation.api.PredictionsLogic;
import execution.simulation.impl.PredictionsLogicImpl;
import menu.api.Menu;
import menu.api.MenuOptions;
import menu.helper.PrintToScreen;
import menu.helper.TypesScanner;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

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
    public static final int XML = 1;
    public static final int FILE = 2;

    private PredictionsLogic engine = new PredictionsLogicImpl();

    @Override
    public void show() {
        int choice = 0;

        initSimulationByFileOrXML();
        while (choice != EXIT) {
            printer.displayMenu();
            choice = typeScanner.getIntFromUserInRange(1, MenuOptions.values().length);

            try {
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
                        exitOrSaveToFile();
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error!");
                if (e.getMessage() != null) {
                    System.out.println(e.getMessage());
                } else {
                    System.out.println("General Error Occurred;");
                }
            }
        }
    }
    private void initSimulationByFileOrXML() {
        int choice = -1;
        System.out.println("Would you like to load the previous state of the simulation or provide an XML path for a new simulation?");
        System.out.println(XML + ". Load new Xml");
        System.out.println(FILE + ". Load previous state");
        choice = typeScanner.getIntFromUserInRange(1, 2);

        switch (choice){
            case XML:
                initSimulationByXML();
                break;
            case FILE:
                initSimulationByFile();
                break;
        }

    }

    private void initSimulationByXML() {
        boolean isValid = false;

        System.out.println("Please provide the full file path to start the simulation");
        while (!isValid) {
            try {
                loadFileFromUser();
                System.out.println("File loaded successfully!");
                System.out.print(System.lineSeparator());
                isValid = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.print(System.lineSeparator());
            }
        }
    }

    private void initSimulationByFile(){
        try (FileInputStream fileInputStream = new FileInputStream("predictions.dat");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            engine = (PredictionsLogicImpl) objectInputStream.readObject();
            System.out.println("File has been deserialized.");
        } catch (FileNotFoundException e) {
            System.out.println("The predictions.dat file is empty or does not exist.");
            System.out.println("Please load an XML file instead.");
            initSimulationByXML();
        } catch (Exception e) {
            System.out.println("An unspecified error has occurred during data retrieval.");
            System.out.println("Please load an XML file instead.");

            initSimulationByXML();
        }
    }

    private void loadXml(){
        try{
            printer.printLoadFileMenu();
            loadFileFromUser();

            System.out.println("File loaded successfully!");
            System.out.print(System.lineSeparator());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println("File did not load.");
            System.out.print(System.lineSeparator());
        }
    }

    private void loadFileFromUser() {
        String filePath;

        filePath = scanner.nextLine().trim();
        engine.loadXML(filePath);
    }

    private void showSimulationDetails() {
        WorldDTO details = engine.getLoadedSimulationDetails();

        printer.printWorldDTO(details);
    }

    private void runSimulation() {
        List<PropertyDefinitionDTO> updatedEnvironmentVariables;
        SimulationRunDetailsDTO runDetails;

        updatedEnvironmentVariables = getEnvironmentVariablesFromUser();
        updatedEnvironmentVariables = engine.setEnvironmentVariables(updatedEnvironmentVariables);

        printer.viewEnvironmentVariablesValues(updatedEnvironmentVariables);
         runDetails = engine.runNewSimulation(updatedEnvironmentVariables);
        printer.printRunDetailsDTO(runDetails);
        System.out.println();
    }

    private void showPreviousSimulation() {
        List<SimulationDTO> allSimulations = engine.getPreviousSimulationsAsDTO();

        if (allSimulations != null && !allSimulations.isEmpty()) {
            int simulationIndex, viewOption;

            printer.printTitle("Get Previous Simulations Data", '~', '-', 0);
            System.out.println();

            printer.showAllSimulationsDTO(allSimulations);
            System.out.println("Please choose a simulation");
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

        chosenEntityName = getEntityNameFromUser(serialNumber);
        chosenPropertyName = getPropertyNameByUser(serialNumber, chosenEntityName);
        data = engine.getSimulationData(serialNumber, chosenEntityName, chosenPropertyName);

        return data;
    }

    private String getEntityNameFromUser(int serialNumber) {
        int entityIndex = 1, count = 1;
        List<String> entitiesNames = engine
                .getSimulationDTOBySerialNumber(serialNumber)
                .getWorld()
                .getEntities()
                .stream().map(EntityDefinitionDTO::getName)
                .collect(Collectors.toList());

        if (entitiesNames.size() != 1) {
            for (String name : entitiesNames) {
                System.out.println(count + ". " + name);
                count++;
            }

            System.out.println("Please choose an entity:");
            entityIndex = typeScanner.getIntFromUserInRange(1, entitiesNames.size());
        }

        return engine
                .getSimulationDTOBySerialNumber(serialNumber)
                .getWorld()
                .getEntities()
                .get(entityIndex - 1)
                .getName();
    }

    private String getPropertyNameByUser(int serialNumber, String entityName) {
        List<PropertyDefinitionDTO> properties;
        int propertyIndex;

        properties = engine.getEntityPropertiesByEntityName(serialNumber, entityName);
        printer.printPropertyDefinitionDTOList(properties, 0, false);
        System.out.println("Please choose property");
        propertyIndex = typeScanner.getIntFromUserInRange(1, properties.size()) - 1;

        return engine
                .getEntityPropertiesByEntityName(serialNumber, entityName)
                .get(propertyIndex)
                .getName();
    }

    private List<PropertyDefinitionDTO> getEnvironmentVariablesFromUser() {
        List<PropertyDefinitionDTO> environmentVariables = engine.getEnvironmentVariablesToSet();
        int choice = -1;

        printer.printTitle("Set Environment Variables", '~', '-', 0);
        System.out.println();

        while (choice != 0) {
            System.out.println("Please enter the number of variable you want to set, or 0 to finish the setup");
            System.out.println();

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

    private void exitOrSaveToFile() {
        int choice = -1;
        System.out.println("Do you want to save current state?");
        System.out.println(YES + ". Yes");
        System.out.println(NO + ". No");
        choice = typeScanner.getIntFromUserInRange(1, 2);
        switch (choice){
            case YES:
                saveSimulationStateToFile();
                break;
            case NO:
                break;
        }

    }

    private void saveSimulationStateToFile() {
        try (FileOutputStream fileOutputStream = new FileOutputStream("predictions.dat");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            objectOutputStream.writeObject(engine);
            System.out.println("Object has been serialized and saved to predictions.dat.");
            objectOutputStream.flush();
        } catch (Exception ignore) {
            System.out.println("An error occurred while attempting to save the data");
        }
    }
}
