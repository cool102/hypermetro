public class MetroLine {

    private static int nextStopNumber = 1;
    private final String name;

    private MetroStop head = null;
    private MetroStop tail = null;

    public MetroLine(String name) {
        this.name = name;
    }

    public void appendMetroStop(String stopName) {
        MetroStop ms = new MetroStop(stopName, this);
        if (head == null) {
            head = ms;
            tail = ms;
            head.setPreviousStop(null);
            tail.setNextStop(null);
        } else {
            tail.setNextStop(ms);
            ms.setPreviousStop(tail);
            tail = ms;
            tail.setNextStop(null);
        }
    }

    public void addHeadMetroStop(String stopName) {
        MetroStop ms = new MetroStop(stopName, this);
        if (head == null) {
            head = ms;
            tail = ms;
            head.setPreviousStop(null);
            tail.setNextStop(null);
        } else {
            ms.setPreviousStop(null);
            ms.setNextStop(head);
            head.setPreviousStop(ms);
            this.head = ms;
        }
    }

    public void removeMetroStop(String stopName) {
        MetroStop currentStop = this.getMetroStopByName(stopName);
        if (currentStop != null) {
            MetroStop previousStop = currentStop.getPreviousStop();
            MetroStop nextStop = currentStop.getNextStop();
            if (previousStop != null) {
                previousStop.setNextStop(nextStop);
            } else {
                head = nextStop;
            }
            if (nextStop != null) {
                nextStop.setPreviousStop(previousStop);
            } else {
                tail = previousStop;
            }
        }
    }

    public MetroStop getMetroStopByName(String stopName) {
        MetroStop currentStop = this.head;
        while (currentStop != null) {
            if (currentStop.getName().equals(stopName)) {
                return currentStop;
            }
            currentStop = currentStop.getNextStop();
        }
        return null;
    }

    public static int nextStopNumber() {
        return nextStopNumber++;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        MetroStop currentStop = this.head;
        sb.append("depot\n");
        while (currentStop != null) {
            sb.append(String.format("%s", currentStop.getName()));
            for (MetroStop ms : currentStop.getTransferLines()) {
                sb.append(String.format(" - %s (%s)", ms.getName(), ms.getMetroLine().getName()));
            }
            sb.append('\n');
            currentStop = currentStop.getNextStop();
        }
        sb.append("depot\n");
        return sb.toString();
    }
}