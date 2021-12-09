package com.example.uts_pbp.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.uts_pbp.Preferences.PreferencesSettings;
import com.example.uts_pbp.Preferences.UserPreferences;
import com.example.uts_pbp.R;
import com.example.uts_pbp.databinding.FragmentJadwalBinding;
import com.example.uts_pbp.models.Jadwal;
import com.example.uts_pbp.models.JadwalResponse;
import com.example.uts_pbp.models.Pengguna;
import com.example.uts_pbp.recyclerViews.RVJadwalAdapter;
import com.example.uts_pbp.retrofit.api.ApiClient;
import com.example.uts_pbp.retrofit.api.ApiInterface;
import com.example.uts_pbp.user.User;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentJadwal extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private FragmentJadwalBinding binding;
    private RVJadwalAdapter jadwalAdapter;

    private View parentView;
    private SwipeRefreshLayout swiperefresh;
    private PreferencesSettings settings;

    private User profil;
    private UserPreferences userPreferences;

    private ApiInterface apiService;
    private ArrayList<Jadwal> listJadwal;

    public FragmentJadwal() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_jadwal, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiService = ApiClient.getClient().create(ApiInterface.class);

        settings = (PreferencesSettings) getActivity().getApplication();

        jadwalAdapter = new RVJadwalAdapter(listJadwal,getActivity());

        parentView = binding.viewJadwal;
        swiperefresh = binding.swiperefresh;
        swiperefresh.setOnRefreshListener(this);

        //cek update Tema
        loadSharedPreferences();
        //cek update mode
        loadSharedPreferencesMode();

        // Set Layout Manager dari recycler view
        binding.rvJadwal.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        //panggil fungsi swipe
        onLoadingSwipe ();

        binding.btnPdf.setOnClickListener(v -> {
            try {
                cetakPdf();
            } catch (FileNotFoundException | DocumentException e) {
                e.printStackTrace();
            }
        });
    }

    //get list jadwal from API
    private void getAllJadwal() {
        Call<JadwalResponse> call = apiService.getAllJadwal();

        swiperefresh.setRefreshing(true);

        call.enqueue(new Callback<JadwalResponse>() {
            @Override
            public void onResponse(Call<JadwalResponse> call,
                                   Response<JadwalResponse> response) {
                if (response.isSuccessful()) {
                    listJadwal = new ArrayList<>();
                    UserPreferences userPreferences = new UserPreferences(getContext());
                    for (int i=0; i<response.body().getJadwalList().size();i++) {
                        if (response.body().getJadwalList().get(i).getNama().equals(userPreferences.getUserLogin().getUsername())) {
                            listJadwal.add(response.body().getJadwalList().get(i));
                        }
                    }
                    jadwalAdapter = new RVJadwalAdapter(listJadwal,getActivity());
                    //jadwalAdapter.setJadwalList(response.body().getJadwalList());
                    binding.setJadwaladapter(jadwalAdapter);
                    swiperefresh.setRefreshing(false);
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        swiperefresh.setRefreshing(false);
                        Toast.makeText(getContext(),
                                jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        swiperefresh.setRefreshing(false);
                        Toast.makeText(getContext(),
                                e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JadwalResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Network error",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    //LOAD PREFERENCENYA INI BUAT NGECEK TAMPILAN AWAL
    private void loadSharedPreferences()
    {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PreferencesSettings.PREFERENCES, MODE_PRIVATE);
        String theme = sharedPreferences.getString(PreferencesSettings.CUSTOM_THEME, PreferencesSettings.LIGHT_THEME);
        settings.setCustomTheme(theme);
        updateView();
    }
    private void updateView() {
        final int black = ContextCompat.getColor(this.getActivity(), R.color.black);
        final int white = ContextCompat.getColor(this.getActivity(), R.color.white);

        if(settings.getCustomTheme().equals(PreferencesSettings.DARK_THEME))
        {
            parentView.setBackgroundColor(black);
        }
        else
        {
            parentView.setBackgroundColor(white);
        }
    }
    private void loadSharedPreferencesMode()
    {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PreferencesSettings.PREFERENCES, MODE_PRIVATE);
        String mode = sharedPreferences.getString(PreferencesSettings.CUSTOM_MODE, PreferencesSettings.PORTRAIT_MODE);
        settings.setCustomMode(mode);
        updateMode();
    }
    private void updateMode(){
        if (settings.getCustomMode().equals(PreferencesSettings.LANDSCAPE_MODE)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else{
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    public void onRefresh() {
        getAllJadwal();
    }

    private void onLoadingSwipe (){
        swiperefresh.post(
                new Runnable() {
                    @Override
                    public void run() {
                        // Set Adapter dari recycler view
                        getAllJadwal();
                    }
                }
        );
    }

    private void cetakPdf() throws FileNotFoundException, DocumentException {
        File folder = getContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);

        if (!folder.exists()) {
            folder.mkdir();
        }

        Date currentTime = Calendar.getInstance().getTime();
        String pdfName = currentTime.getTime() + ".pdf";

        File pdfFile = new File(folder.getAbsolutePath(), pdfName);
        OutputStream outputStream = new FileOutputStream(pdfFile);

        com.itextpdf.text.Document document = new
                com.itextpdf.text.Document(PageSize.A4);
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // bagian header
        Paragraph judul = new Paragraph("JADWAL BOOKING SALON \n\n",
                new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 16,
                        Font.BOLD, BaseColor.BLACK));

        judul.setAlignment(Element.ALIGN_CENTER);
        document.add(judul);

        // Buat tabel
        PdfPTable tables = new PdfPTable(new float[]{16, 8});

        // Settingan ukuran tabel
        tables.getDefaultCell().setFixedHeight(50);
        tables.setTotalWidth(PageSize.A4.getWidth());
        tables.setWidthPercentage(100);
        tables.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        PdfPCell cellSupplier = new PdfPCell();
        cellSupplier.setPaddingLeft(20);
        cellSupplier.setPaddingBottom(10);
        cellSupplier.setBorder(Rectangle.NO_BORDER);

        // ambil data user dari view model
        Pengguna user = new Pengguna();

        userPreferences = new UserPreferences(this.getContext());
        profil = userPreferences.getUserLogin();

        Paragraph kepada = new Paragraph(
                "Kepada Yth: \n" + profil.getUsername() + "\n",
                new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 10,
                        Font.NORMAL, BaseColor.BLACK));
        cellSupplier.addElement(kepada);
        tables.addCell(cellSupplier);

        Paragraph NomorTanggal = new Paragraph(
                "No : " + "123456789" + "\n\n" +
                        "Tanggal : " + new SimpleDateFormat("dd/MM/yyyy",
                        Locale.getDefault()).format(currentTime) + "\n",
                new
                        com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                        com.itextpdf.text.Font.NORMAL, BaseColor.BLACK));

        NomorTanggal.setPaddingTop(5);
        tables.addCell(NomorTanggal);
        document.add(tables);
        com.itextpdf.text.Font f = new
                com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);

        Paragraph Pembuka = new Paragraph("\nBerikut merupakan list jadwal: \n\n", f);
        Pembuka.setIndentationLeft(20);
        document.add(Pembuka);
        PdfPTable tableHeader = new PdfPTable(new float[]{5, 5, 5, 5, 5});

        tableHeader.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableHeader.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableHeader.getDefaultCell().setFixedHeight(30);
        tableHeader.setTotalWidth(PageSize.A4.getWidth());
        tableHeader.setWidthPercentage(100);

        // Setup Column
        PdfPCell h1 = new PdfPCell(new Phrase("Nama User"));
        h1.setHorizontalAlignment(Element.ALIGN_CENTER);
        h1.setPaddingBottom(5);
        PdfPCell h2 = new PdfPCell(new Phrase("Produk"));
        h2.setHorizontalAlignment(Element.ALIGN_CENTER);
        h2.setPaddingBottom(5);
        PdfPCell h3 = new PdfPCell(new Phrase("Petugas"));
        h3.setHorizontalAlignment(Element.ALIGN_CENTER);
        h3.setPaddingBottom(5);
        PdfPCell h4 = new PdfPCell(new Phrase("Tanggal booking"));
        h4.setHorizontalAlignment(Element.ALIGN_CENTER);
        h4.setPaddingBottom(5);
        PdfPCell h5 = new PdfPCell(new Phrase("Nomor Telepon"));
        h5.setHorizontalAlignment(Element.ALIGN_CENTER);
        h5.setPaddingBottom(5);

        tableHeader.addCell(h1);
        tableHeader.addCell(h2);
        tableHeader.addCell(h3);
        tableHeader.addCell(h4);
        tableHeader.addCell(h5);

        // Beri warna untuk kolom table
        for (PdfPCell cells : tableHeader.getRow(0).getCells()) {
            cells.setBackgroundColor(BaseColor.PINK);
        }

        document.add(tableHeader);
        PdfPTable tableData = new PdfPTable(new float[]{5, 5, 5, 5, 5});

        tableData.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableData.getDefaultCell().setFixedHeight(30);
        tableData.setTotalWidth(PageSize.A4.getWidth());
        tableData.setWidthPercentage(100);
        tableData.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        // masukan data jadwal jadi baris
        int i = 1;
        for (Jadwal P : listJadwal) {
            i = i+1;
            tableData.addCell(P.getNama());
            tableData.addCell(P.getPelayanan());
            tableData.addCell(P.getPetugas());
            tableData.addCell(P.getTanggal());
            tableData.addCell(P.getTelp());
        }

        document.add(tableData);

        com.itextpdf.text.Font h = new
                com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                com.itextpdf.text.Font.NORMAL);

        String tglDicetak = currentTime.toLocaleString();
        Paragraph P = new Paragraph("\nDicetak tanggal " + tglDicetak, h);
        P.setAlignment(Element.ALIGN_RIGHT);
        document.add(P);
        document.close();
        previewPdf(pdfFile);
        Toast.makeText(getContext(), "PDF berhasil dibuat", Toast.LENGTH_SHORT).show();
    }

    private void previewPdf(File pdfFile) {
        PackageManager packageManager = getContext().getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(testIntent,
                        PackageManager.MATCH_DEFAULT_ONLY);

        if (list.size() > 0) {
            Uri uri;
            uri = FileProvider.getUriForFile(getContext(), getContext().getPackageName() + ".provider", pdfFile);
            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
            pdfIntent.setDataAndType(uri, "application/pdf");
            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pdfIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            pdfIntent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            pdfIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            getContext().grantUriPermission(getContext().getPackageName(), uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION |
                            Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(pdfIntent);
        }
    }
}
