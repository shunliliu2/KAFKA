package com.pdm;

import com.alibaba.fastjson.JSONArray;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Stream;

import static com.pdm.mysql.gethttp;

@RestController
@RequestMapping("/mysql")
public class csv {
    @RequestMapping(value = "/dopost2", method = RequestMethod.POST)
    @ResponseBody
    public String execute(String url ,Integer qq,String ser) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

        //List<String> files = getFiles("${fileurl}");
            String charset = "utf-8";
        try (CSVReader csvReader = new CSVReaderBuilder(new BufferedReader(new InputStreamReader(new FileInputStream(new File(url)), charset))).build()) {
            List<String[]> strings = csvReader.readAll();
            strings.remove(0);
            ListIterator<String[]> iterator = strings.listIterator();
            User user = new User();
            int count =0;
            while (iterator.hasNext()) {
                Stream<String> stream = Arrays.stream(iterator.next());
                Object [] sss = stream.toArray();
                user.setItemId((String) sss[0]);
                user.setTitle((String)sss[1]);
                user.setOriginalname((String)sss[2]);
                user.setOriginalname((String)sss[3]);
                user.setSearchname((String)sss[4]);
                user.setActordisplay((String)sss[5]);
                user.setOriginalcountry((String)sss[6]);
                user.setLanguage((String)sss[7]);
                user.setOrgairdate((String)sss[8]);
                user.setLicensingwindowstart((String) sss[9]);
                user.setLicensingwindowemd((String) sss[10]);
                user.setContent((String)sss[11]);
                user.setPricetaxin((String)sss[12]);
                user.setCasts((String) sss[13]);
                user.setDirectors((String) sss[14]);
                user.setScriptwriter((String) sss[15]);
                user.setCompere((String) sss[16]);
                user.setGuest((String) sss[17]);
                user.setReporter((String) sss[18]);
                user.setOpincharge((String) sss[19]);

                    user.setSeriesflag((String) sss[20]);

                user.setVolumcount((String) sss[21]);
                user.setSourcetype((String) sss[22]);
                user.setGener((String) sss[23]);
                try {
                    user.setPublishTime(Long.parseLong((String)(sss[24])));
                } catch (Exception e) {
                    user.setPublishTime(31507200000L);
                }
                user.setVspcode((String) sss[25]);
                user.setSeriestype(ser);
                user.setDuration((String) sss[27]);
                user.setDuration1((String) sss[28]);
                try {
                    user.setShichang2(Integer.parseInt((String) sss[27]));
                } catch (NumberFormatException e) {

                }
                user.setCopyright((String) sss[29]);
                //user.setPublisherId((String) sss[28]);
                user.setContentprovider((String) sss[30]);
                user.setLinkurl((String) sss[31]);
                user.setPgmcategory((String) sss[32]);user.setTag((String) sss[32]);
                user.setPgmsndclass((String) sss[33]);
                try {
                    user.setRating(Double.parseDouble((String)sss[34]));
                } catch (Exception e) {

                }
                user.setTags((String) sss[35]);
                user.setHou_tag((String) sss[36]);
                user.setRegion((String) sss[37]);

                try {
                    user.setDb_score(Double.parseDouble((String)sss[38]));
                } catch (NumberFormatException e) {

                }
                try {
                    user.setHu_score(Double.parseDouble((String)sss[39]));
                } catch (NumberFormatException e) {
                }
                user.setVideo_type((String) sss[40]);

                user.setShort_desc((String) sss[41]);

                user.setType1((String) sss[42]);
                user.setType1_url((String) sss[43]);
                user.setType2((String) sss[44]);
                user.setType2_url((String) sss[45]);
                user.setUrl((String) sss[46]);
                user.setCategoryLevel1((String) sss[51]);
                user.setCategoryLevel2((String) sss[50]);
                user.setCategoryLevel3((String) sss[49]);
                user.setCategoryLevel4((String) sss[48]);
                user.setCategoryLevel5((String) sss[47]);



                user.setItemSetId(qq);
                user.setPublisherId((String) sss[30]);
                user.setItemStatus("");
                user.setCoverUrl("");


                System.out.println("user = " + user);
                Object oo = JSONArray.toJSON(user);
                String listjson = oo.toString();
                System.out.println("listjson = " + listjson);
                System.out.println("count = " + count);
                Thread.sleep(50);
                count++;
                gethttp(listjson,qq);


            }




        }

        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return "hello world";
    }

    public static void main(String[] args) {

        String charset = "utf-8";
        try (CSVReader csvReader = new CSVReaderBuilder(new BufferedReader(new InputStreamReader(new FileInputStream(new File("url")), charset))).build()) {
            List<String[]> strings = csvReader.readAll();
            strings.remove(0);
            ListIterator<String[]> iterator = strings.listIterator();
            User user = new User();
            while (iterator.hasNext()) {
                Stream<String> stream = Arrays.stream(iterator.next());
                Object [] sss = stream.toArray();
                    user.setItemId((String) sss[0]);
                    user.setTitle((String)sss[1]);
                    user.setOriginalname((String)sss[2]);
                    user.setSearchname((String)sss[4]);
                    user.setContent((String)sss[11]);
                    user.setCasts((String) sss[13]);
                    user.setDirectors((String) sss[14]);
                    user.setScriptwriter((String) sss[15]);
                    user.setCompere((String) sss[16]);
                    user.setGuest((String) sss[17]);
                    user.setReporter((String) sss[18]);
                    user.setOpincharge((String) sss[19]);
                    user.setLicensingwindowstart((String) sss[9]);
                    user.setLicensingwindowemd((String) sss[10]);
                    user.setVolumcount((String) sss[77]);
                    user.setVspcode((String) sss[24]);
                    user.setPublishTime(System.currentTimeMillis());
                    user.setSeriestype((String) sss[78]);
                    user.setCopyright((String) sss[27]);
                    user.setPublisherId((String) sss[28]);
                    user.setTag((String) sss[37]);
                    user.setPgmsndclass((String) sss[31]);
                    user.setRating((Double) sss[77]);
                    user.setOriginalcountry((String) sss[77]);
                    user.setType1((String) sss[33]);
                    user.setType1_url((String) sss[34]);
                    user.setType2((String) sss[35]);
                    user.setType2_url((String) sss[36]);
                    user.setItemSetId(25);
                    user.setItemStatus((String) sss[77]);
                    user.setItemTime((String) sss[38]);
                    user.setCoverUrl((String) sss[77]);
                    user.setUrl((String) sss[77]);
                    System.out.println("user = " + user);
                    Object oo = JSONArray.toJSON(user);
                    String listjson = oo.toString();
                    System.out.println("listjson = " + listjson);
                    gethttp(listjson,25);

                }




        }

        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}
