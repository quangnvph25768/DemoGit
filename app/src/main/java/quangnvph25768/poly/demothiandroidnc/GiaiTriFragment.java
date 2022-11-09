package quangnvph25768.poly.demothiandroidnc;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class GiaiTriFragment extends Fragment {

    ListView lvRss;
    ListNewsAdapter adapter;
    ArrayList<DocBao> listNews;
    List<BaiBao> list;
    BaiBao baiBao;
    NewsDAO dao;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_giai_tri, container, false);
        lvRss = view.findViewById(R.id.lvRss);

        listNews = new ArrayList<DocBao>();
        list = new ArrayList<BaiBao>();
        lvRss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("link", list.get(position).link);
                startActivity(intent);
            }
        });

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadData().execute("https://vnexpress.net/rss/giai-tri.rss");

            }
        });
        return view;
    }

    class ReadData extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            return docNoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {

            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");
            NodeList nodeListdescription = document.getElementsByTagName("description");
            String title = "";
            String link = "";
            String hinhAnh = "";
            for (int i = 0; i < nodeList.getLength(); i++) {
                String cdata = nodeListdescription.item(i + 1).getTextContent();

                Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                Matcher matcher = p.matcher(cdata);
                if (matcher.find()) {
                    hinhAnh = matcher.group(1);
                }

                baiBao = new BaiBao();
                dao = new NewsDAO(getContext());
                Element element = (Element) nodeList.item(i);
                title = parser.getValue(element, "title");
                link = parser.getValue(element, "link");

                baiBao.setTitle(title);
                baiBao.setLink(link);

                dao.insertNews(baiBao);
                list = dao.getAll();
                Log.d("Title", title);
                listNews.add(new DocBao(title, link, hinhAnh));
            }
            adapter = new ListNewsAdapter(getContext(), android.R.layout.simple_list_item_1, list);
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
