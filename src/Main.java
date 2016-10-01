import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final String fileName = "D:\\PROG KIEV UA\\LESSON 2\\Task 1\\task1.xml";
    static int countTrains;

    public static void main(String[] args) {
        actionsWithTrainList();
    }

    public static void actionsWithTrainList() {
        Trains trains = new Trains();
        trains = fromXmlToObject(fileName);
        if (trains != null) {
            for (Train train :
                    trains.getTrains()) {
                countTrains = countTrains + 1;
            }
        }
        while (true) {
            System.out.println("1. Add new train");
            System.out.println("2. Print train list");
            System.out.println("3. Exit");

            int n3;
            try {
                String num3 = br.readLine();
                n3 = Integer.parseInt(num3);
            } catch (Exception ex) {
                n3 = 1;
            }

            if (n3 == 1) addTrain(trains);
            if (n3 == 2) printTrainList(trains);
            if (n3 == 3) break;
        }
    }

    public static void addTrain(Trains trains) {
        while (true) {

            System.out.println("Insert from: ");
            String from = "";
            try {
                from = br.readLine();
            } catch (Exception e) {

            }

            System.out.println("Insert to: ");
            String to = "";
            try {
                to = br.readLine();
            } catch (Exception e) {

            }

            System.out.println("Insert date: ");
            String date = "";
            try {
                date = br.readLine();
            } catch (Exception e) {

            }

            System.out.println("Insert depature: ");
            String depature = "";
            try {
                depature = br.readLine();
            } catch (Exception e) {

            }

            countTrains = countTrains + 1;
            Train train = new Train(countTrains, from, to, date, depature);
            trains.add(train);
            try {
                File file = new File(fileName);
                JAXBContext jaxbContext = JAXBContext.newInstance(Trains.class);
                Marshaller marshaller = jaxbContext.createMarshaller();

                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                marshaller.marshal(trains, file);

            } catch (JAXBException e) {
                e.printStackTrace();
            }

            System.out.println("Do you want to add one more train? (Y/N)");
            String res = "";
            try {
                res = br.readLine();
            } catch (Exception e) {

            }
            if (res.equals("N") || res.equals("n")) return;
        }
    }

    public static void printTrainList(Trains trains) {
        Trains unmarshTrains = fromXmlToObject(fileName);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

        if (unmarshTrains != null) {
            for (Train train :
                    unmarshTrains.getTrains()) {

                String depature = train.getDeparture();
                if (depature != null || depature != "") {
                    try {
                        Date timeDeparture = simpleDateFormat.parse(train.getDeparture());
                        Date timeStart = simpleDateFormat.parse("15:00");
                        Date timeEnd = simpleDateFormat.parse("19:00");
                        if (timeDeparture.after(timeStart) && timeDeparture.before(timeEnd) )
                            System.out.println(train.toString());

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    private static Trains fromXmlToObject(String filePath) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Trains.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            return (Trains) unmarshaller.unmarshal(new File(filePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

}
