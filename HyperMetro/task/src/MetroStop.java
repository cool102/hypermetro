import java.util.HashSet;
import java.util.Set;

public class MetroStop implements Comparable<MetroStop> {
    private final int number;
    private final String name;
    private final MetroLine metroLine;
    private final Set<MetroStop> transferLines = new HashSet<>();

    private MetroStop previousStop = null;
    private MetroStop nextStop = null;

    public MetroStop(String name, MetroLine metroLine) {
        super();
        this.name = name;
        this.metroLine = metroLine;
        this.number = MetroLine.nextStopNumber();
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public MetroStop getNextStop() {
        return nextStop;
    }

    public void setNextStop(MetroStop nextStop) {
        this.nextStop = nextStop;
    }

    public MetroStop getPreviousStop() {
        return previousStop;
    }

    public void setPreviousStop(MetroStop previousStop) {
        this.previousStop = previousStop;
    }

    public MetroLine getMetroLine() {
        return metroLine;
    }

    public Set<MetroStop> getTransferLines() {
        return transferLines;
    }

    public void addTransferLine(MetroStop metroStop) {
        if (!this.equals(metroStop)) {
            this.transferLines.add(metroStop);
        }
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(number).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MetroStop) {
            MetroStop ms = (MetroStop) obj;
            return ms.getNumber() == this.getNumber();
        }
        return false;
    }

    @Override
    public int compareTo(MetroStop o) {
        return this.getNumber() - o.getNumber();
    }
}
