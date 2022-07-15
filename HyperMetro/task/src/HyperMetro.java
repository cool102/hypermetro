import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HyperMetro {

    private final Map<String, MetroLine> metroLines = new HashMap<>();

    public HyperMetro(String fileName) {
        loadMetroLines(fileName);
    }

    public void runApp() {
        while (true) {
            String[] parsedCommand = getUserCommand();
            if (parsedCommand[0].equals("/exit")) {
                System.exit(0);
            } else if (parsedCommand[0].equals("/append") && parsedCommand.length == 3) {
                MetroLine ml = metroLines.get(parsedCommand[1]);
                if (ml == null) {
                    ml = new MetroLine(parsedCommand[1]);
                    metroLines.put(parsedCommand[1], ml);
                }
                ml.appendMetroStop(parsedCommand[2]);
            } else if (parsedCommand[0].equals("/add-head") && parsedCommand.length == 3) {
                MetroLine ml = metroLines.get(parsedCommand[1]);
                if (ml == null) {
                    ml = new MetroLine(parsedCommand[1]);
                    metroLines.put(parsedCommand[1], ml);
                }
                ml.addHeadMetroStop(parsedCommand[2]);
            } else if (parsedCommand[0].equals("/remove") && parsedCommand.length == 3) {
                MetroLine ml = metroLines.get(parsedCommand[1]);
                if (ml == null) {
                    System.out.println("Invalid command");
                } else {
                    ml.removeMetroStop(parsedCommand[2]);
                }
            } else if (parsedCommand[0].equals("/output") && parsedCommand.length == 2) {
                MetroLine ml = metroLines.get(parsedCommand[1]);
                if (ml == null) {
                    System.out.println("Invalid command");
                } else {
                    System.out.println(ml);
                }
            } else if (parsedCommand[0].equals("/connect") && parsedCommand.length == 5) {
                MetroLine ml1 = metroLines.get(parsedCommand[1]);
                MetroLine ml2 = metroLines.get(parsedCommand[3]);
                if (ml1 != null && ml2 != null) {
                    MetroStop ms1 = ml1.getMetroStopByName(parsedCommand[2]);
                    MetroStop ms2 = ml2.getMetroStopByName(parsedCommand[4]);
                    if (ms1 != null && ms2 != null) {
                        ms1.addTransferLine(ms2);
                        ms2.addTransferLine(ms1);
                    } else {
                        System.out.println("Invalid command");
                    }
                }
            } else {
                System.out.println("Invalid command");
            }
        }
    }

    private String[] getUserCommand() {
        Scanner scanner = new Scanner(System.in);
        String[] parsedCommand = null;
        while (true) {
            try {
                parsedCommand = parseCommand(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid command");
            }
            if (parsedCommand == null || parsedCommand.length == 0 || !parsedCommand[0].startsWith("/")) {
                System.out.println("Invalid command");
            } else {
                break;
            }
        }
        return parsedCommand;
    }

    private String[] parseCommand(String command) {
        Pattern regex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"");
        Matcher regexMatcher = regex.matcher(command);
        List<String> matchList = new ArrayList<>();
        while (regexMatcher.find()) {
            if (regexMatcher.group(1) != null) {
                matchList.add(regexMatcher.group(1));
            } else {
                matchList.add(regexMatcher.group());
            }
        }
        return matchList.toArray(String[]::new);
    }

    public void loadMetroLines(String fileName) {
        MetroLineData metroLineData = null;
        Gson gson = new Gson();
        try (FileReader fr = new FileReader(fileName)) {
            metroLineData = gson.fromJson(fr, MetroLineData.class);
        } catch (FileNotFoundException e) {
            System.out.println("Error! Such a file doesn't exist!");
            System.exit(-1);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        Map<MetroStop, MetroLineData.TransferData[]> transfers = new HashMap<>();
        for (String metroLineName : metroLineData.keySet()) {
            Map<String, MetroLineData.MetroStationData> metroStations = metroLineData.get(metroLineName);
            MetroLine ml = new MetroLine(metroLineName);
            int[] stationIds = metroStations.keySet().stream().mapToInt(Integer::parseInt).sorted().toArray();
            for (int stationId : stationIds) {
                MetroLineData.MetroStationData metroStationData = metroStations.get(String.valueOf(stationId));
                ml.appendMetroStop(metroStationData.name);
                if (metroStationData.transfer != null && metroStationData.transfer.length > 0) {
                    transfers.put(ml.getMetroStopByName(metroStationData.name), metroStationData.transfer);
                }
            }
            metroLines.put(metroLineName, ml);
        }
        for (MetroStop ms : transfers.keySet()) {
            for (MetroLineData.TransferData td : transfers.get(ms)) {
                MetroLine line = metroLines.get(td.line);
                MetroStop stop = line.getMetroStopByName(td.station);
                stop.addTransferLine(ms);
                ms.addTransferLine(stop);
            }
        }
    }

    static class MetroLineData extends TreeMap<String, TreeMap<String, MetroLineData.MetroStationData>> {
        static class MetroStationData {
            public String name;
            public TransferData[] transfer;
        }

        static class TransferData {
            public String line;
            public String station;
        }
    }
}
