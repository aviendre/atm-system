package main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Java file that contains the functions that handles the extraction of the data, 
 * checking of transaction details to be in the correct format, and retrieving the
 * encrypted account information stored locally.
 */
public class CSVparser{
    private final String oldInputPath = "resources/bank_sample_01.csv";
    private final String oldOutputPath = "resources/bank_badcase.csv";
    private final String accountDetailsPath = "resources/bank_account.csv";
    //private final String accountDetailsPathc = "resources/bank_account_copy.csv";
    //private final String transactionDetailsPath = "src/resources/transactions_parse.csv";

    /**
     * Encrypts any file based on Caesar Encryption. Key stored in the Caesar Encryption
     * @param filePath path that requires encrypting
     */
    public void encryptCSV(String filePath){
        try{
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String inputLine;
            String output = "";
            while((inputLine = br.readLine()) != null && !inputLine.isEmpty()){
                output += CaeserEncryption.encrypt(inputLine) + "\n";
            }
            br.close();
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
            bw.append(output);
            bw.close();
        } catch(IOException io){ io.printStackTrace();}
    }

    /**
     * Parses through all the encrypted account details passed to the ATM system
     * and decrypts it for the program to run. 
     * @return List of Account objects
     */
    public List<Account> parseAccountDetails(){
        List<Account> Accounts = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(accountDetailsPath));
            String inputLine = br.readLine();
            while((inputLine = br.readLine()) != null && !inputLine.isEmpty()){
                inputLine = CaeserEncryption.decrypt(inputLine);
                String[] data = inputLine.split(",");
                Account x = new Account(data[0],
                                        data[1],
                                        Long.parseLong(data[2]),
                                        data[3],
                                        Double.parseDouble(data[4]));
                Accounts.add(x);
            }
            br.close();
        } catch (Exception fe) { return null; }
        return Accounts;
    }

    /**
     * Parses through the bank_sample_1.csv to retrieve the old history of transactions
     * that the bank provides so it can be referred to in the future.
     * @return List of Transactions objects
     */
    public List<Transaction> parseOldDataSet(){
        try{
            List<Transaction> transactions = new ArrayList<>();
            long cN = 0;
            double wA=0, dA = 0;

            BufferedReader br = new BufferedReader(new FileReader(oldInputPath));
            String inputLine = br.readLine(); //Read Header

            File csvOutput = new File(oldOutputPath);
            PrintWriter pw = new PrintWriter(csvOutput);

            while ((inputLine = br.readLine()) != null && !inputLine.isEmpty() ){
                try{
                    String[] data = inputLine.split(",");
                    if(!data[3].isEmpty()){cN = Long.parseLong(data[3]);}
                    if(!data[5].isEmpty()){wA = Double.parseDouble(data[5]);}
                    if(!data[6].isEmpty()){dA =  Double.parseDouble(data[6]);}
                    Transaction transaction = new Transaction(Long.parseLong(data[0].substring(0,data[0].length()-1))
                                                            ,LocalDate.parse(data[1],DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                                                            , data[2]
                                                            , cN
                                                            , LocalDate.parse(data[4],DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                                                            , wA
                                                            , dA);
                    transactions.add(transaction);
                    wA = 0;
                    dA = 0;
                    cN = 0;
                } catch(Exception e) {
                    try {
                        pw.append(inputLine.toString() +","+ e + "\n");
                    } catch(Exception fe){
                        fe.printStackTrace();
                        continue;
                    }
                    continue;
                }
            }
            br.close();
            pw.close();
            return transactions;
        } catch(FileNotFoundException fnfe) {
            System.out.println("File not found");
            fnfe.printStackTrace();
            return null;
        } catch(Exception e){
            e.printStackTrace();
            return null;
        } 
    } 

}
