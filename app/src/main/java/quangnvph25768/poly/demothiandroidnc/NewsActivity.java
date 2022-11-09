package quangnvph25768.poly.demothiandroidnc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import quangnvph25768.poly.demothiandroidnc.service.MyService;

public class NewsActivity extends AppCompatActivity {
    ListView lvRss;
    ListNewsAdapter adapter;

    List<BaiBao> list;
    BaiBao baiBao;
    NewsDAO dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        lvRss = findViewById(R.id.lvView);
        list = new ArrayList<BaiBao>();

        lvRss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NewsActivity.this, MainActivity.class);
                intent.putExtra("link", list.get(position).link);
                startActivity(intent);
                Intent intent1 = new Intent(NewsActivity.this, MyService.class);
                startService(intent1);
            }
        });

            runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadDataa().execute("https://thanhnien.vn/rss/video/mon-ngon-350.rss");
                Toast.makeText(NewsActivity.this, "Load thanh cong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    class ReadDataa extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            return docNoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {

            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");
            //NodeList nodeListdescription = document.getElementsByTagName("description");
            String title = "";
            String link = "";
            String hinhAnh = "";
            for (int i = 0; i < nodeList.getLength(); i++) {
                //String cdata = nodeListdescription.item(i + 1).getTextContent();

                //Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                //Matcher matcher = p.matcher(cdata);
                //if (matcher.find()) {
                //    hinhAnh = matcher.group(1);
                //}

                baiBao = new BaiBao();
                dao = new NewsDAO(NewsActivity.this);
                Element element = (Element) nodeList.item(i);
                title = parser.getValue(element, "title");
                link = parser.getValue(element, "link");

                baiBao.setTitle(title);
                baiBao.setLink(link);

                dao.insertNews(baiBao);
                list = dao.getAll();
                Log.d("Title", title);

            }
            adapter = new ListNewsAdapter(NewsActivity.this, android.R.layout.simple_list_item_1, list);
            lvRss.setAdapter(adapter);

            super.onPostExecute(s);
        }
    }

    private static String docNoiDung_Tu_URL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {

            URL url = new URL(theUrl);
            URLConnection urlConnection = url.openConnection();

            InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}