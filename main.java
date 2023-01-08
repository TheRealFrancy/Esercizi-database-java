 import com.mongodb.client.*;
    import com.mongodb.client.model.Filters;
    import com.mongodb.client.model.Updates;
    import org.bson.Document;
    import org.bson.conversions.Bson;
    import javax.swing.JOptionPane;
    import java.util.Random;
    public class Main {


    public static void main(String[] args) {
    Random rand = new Random(); // inizializzazione metodo random
    MongoClient mongoClient=MongoClients.create("mongodb+srv://therealfrancydev:password@francymaincluster.opxrg.mongodb.net/?retryWrites=true&w=majority");
    MongoDatabase database = mongoClient.getDatabase("banksystemdb");
    MongoCollection col=database.getCollection("bankaccount");
    // end mongo setup
    boolean next=true;
    boolean nextdue=true;
    while(next) {
    // seleziona il conto sui cui agire
    String[] options = {"crea", "modifica","close"};
    int action;
    action = JOptionPane.showOptionDialog(null, "cosa vuoi fare?", "opzioni conti", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    if (action == JOptionPane.YES_OPTION) {
    String creaconto = JOptionPane.showInputDialog("inserisci nome conto"); // insert bank account name

    char[] uniqueidchar = {'a', 'b', 'c', 'd', 'e', 'h', 'l', 'm', 'n', 'k', '1', '2', '3', '4', '5'};
    char[] randomidchar = new char[10];
    for (int i = 0; i < 10; i++) {
    int indiceidchar = rand.nextInt(uniqueidchar.length);
    randomidchar[i] = uniqueidchar[indiceidchar];
    }//end for random char
    String listuniqueid = new String(randomidchar);
    // end unique id creation


    Document sampledoc = new Document("uniquedid", listuniqueid).append("name", creaconto).append("cash", 0);
    col.insertOne(sampledoc);


    } else if (action == JOptionPane.NO_OPTION) {

    while(nextdue) {
    String loginconto = JOptionPane.showInputDialog("inserisci il codice univoco per modificare il conto");




    /* Bson filter = Filters.eq("uniquedid",loginconto);
    try {
    Document doc = (Document) col.find(filter).first();

    Object valuecashmore= doc.get("cash");

    System.out.println(doc);
    System.out.println(valuecashmore);


    }catch (Exception e){

    System.out.println("errore");
    }*/

    String[] options2 = {"aggiungi", "rimuovi","home"};
    int action2;

    action2 = JOptionPane.showOptionDialog(null, "finestra di modifica soldi", "", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options2, options2[0]);


    if (action2 == JOptionPane.YES_OPTION) {
    Bson filtercashmore = Filters.eq("uniquedid", loginconto);
    Document doc = (Document) col.find(filtercashmore).first();
    int valuecashmore = (int) doc.get("cash");


    String howcash = JOptionPane.showInputDialog("quanti soldi vuoi aggiungere? il conto ha " + valuecashmore + "$");
    valuecashmore = valuecashmore + Integer.parseInt(howcash);


    col.updateOne(Filters.eq("uniquedid", loginconto), Updates.set("cash", valuecashmore));



    } else if (action2 == JOptionPane.NO_OPTION) {
    Bson filtercashless = Filters.eq("uniquedid", loginconto);
    Document doc = (Document) col.find(filtercashless).first();
    int valuecashless = (int) doc.get("cash");


    String howcash2 = JOptionPane.showInputDialog("quanti soldi vuoi rimuovere? il conto ha " + valuecashless + "$");
    valuecashless = valuecashless - Integer.parseInt(howcash2);


    col.updateOne(Filters.eq("uniquedid", loginconto), Updates.set("cash", valuecashless));


    } else if (action2==JOptionPane.CANCEL_OPTION) {
    nextdue=false;



    }


    } // end while due
    } else if (action==JOptionPane.CANCEL_OPTION) {
    next=false;



    }


    }// while one

    System.exit(1);

    }// end main

    } //end class
