package com.example.appgym.control.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appgym.R;
import com.example.appgym.adapter.RecyclerAPIAdapter;
import com.example.appgym.adapter.RecyclerDetailAdapter;
import com.example.appgym.adapter.SpinnerAdapter;
import com.example.appgym.model.ClickListener;
import com.example.appgym.model.Days;
import com.example.appgym.model.Ejercicio;
import com.example.appgym.model.EjercicioResponse;
import com.example.appgym.model.RutinaDTO;
import com.example.appgym.persistencia.api.ApiClient;
import com.example.appgym.persistencia.api.WgerApi;
import com.example.appgym.repository.RutinaRepositoryImpl;
import com.example.appgym.utils.Validation;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewRoutineFragment extends BaseFragment {

    private RutinaRepositoryImpl rutinaRepository;
    private TextView textEjercicio, textRutina;
    private TextWatcher textWatcher;
    private Button btnNewEjercicio, btnCreateRoutine;
    private RecyclerView recycler;
    private RecyclerDetailAdapter recyclerAdapter;
    private RecyclerAPIAdapter recyclerAPIAdapter;
    private List<Ejercicio> ejercicios;
    private List<EjercicioResponse> ejerciciosResponse;
    private Ejercicio ejercicioTemp;
    private Spinner spinner;
    private SpinnerAdapter spinnerAdapter;
    private List<String> days = Days.getAll();
    private int title = R.string.title_new_routine;
    private int menu = 0;
    public NewRoutineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_routine, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textRutina = view.findViewById(R.id.textRutina);
        btnNewEjercicio = view.findViewById(R.id.btnNewEjercicio);
        btnCreateRoutine = view.findViewById(R.id.btnCreateRoutine);

        setDataRecycler(view);
        setDataSpinner(view);

        btnNewEjercicio.setOnClickListener(v -> {
            showNewDialog();
        });

        btnCreateRoutine.setOnClickListener(v -> {
            try {
                createRoutine();
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });

        setMenu(getString(title), menu);
    }

    private void setDataRecycler(@NonNull View view) {
        recycler = view.findViewById(R.id.recycler);
        ejercicios = new ArrayList<>();
        recyclerAdapter = new RecyclerDetailAdapter(ejercicios, false);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recycler.setAdapter(recyclerAdapter);
    }

    private void setDataSpinner(@NonNull View view) {
        spinner = view.findViewById(R.id.spinner);
        spinnerAdapter = new SpinnerAdapter(getContext(), R.layout.spinner_item, days);
        spinner.setAdapter(spinnerAdapter);
    }

    private void showNewDialog() {
        final int[] cantidadSeries = {0};
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_ejercicio, null);
        LinearLayout contenedorTexts = dialogView.findViewById(R.id.contenedorTexts);
        ImageView btnAddRow = dialogView.findViewById(R.id.btnAddRow);
        ImageView btnDeleteRow = dialogView.findViewById(R.id.btnDeleteRow);
        textEjercicio = dialogView.findViewById(R.id.textEjercicio);
        RecyclerView recyclerAPI = dialogView.findViewById(R.id.recyclerAPI);
        ejerciciosResponse = new ArrayList<>();

        setTextWatcher();

        recyclerAPIAdapter = new RecyclerAPIAdapter(ejerciciosResponse, new ClickListener() {
            @Override
            public void onClick(int position) {
                textEjercicio.removeTextChangedListener(textWatcher);
                EjercicioResponse ejercicioResponse = ejerciciosResponse.get(position);
                if (ejercicioResponse != null){
                    textEjercicio.setText(ejercicioResponse.getNombre());
                    ejercicioTemp = new Ejercicio();
                    ejercicioTemp.setNombre(ejercicioResponse.getNombre());
                    ejercicioTemp.setImagen(ejercicioResponse.getImagen());
                    textEjercicio.addTextChangedListener(textWatcher);
                }
            }
        });
        recyclerAPI.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerAPI.setAdapter(recyclerAPIAdapter);


        textEjercicio.addTextChangedListener(textWatcher);

        addRow(cantidadSeries, contenedorTexts);

        btnAddRow.setOnClickListener(view -> {
            addRow(cantidadSeries, contenedorTexts);
        });
        btnDeleteRow.setOnClickListener(view -> {
            deleteRow(cantidadSeries, contenedorTexts);
        });

        new AlertDialog.Builder(getContext())
                .setView(dialogView)
                .setTitle("Crear Ejercicio")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        createEjercicio(contenedorTexts);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .create()
                .show();

    }

    private void setTextWatcher() {
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ejerciciosResponse.clear();
                recyclerAPIAdapter.notifyDataSetChanged();
                searchExercises(charSequence.toString());
                ejercicioTemp = null;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }

    private void searchExercises(String nombre) {
        WgerApi api = ApiClient.getWgerApi();
        api.searchExercises(nombre).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JsonElement jsonElement = response.body();
                if (jsonElement.isJsonObject()){
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    if (jsonObject.has("suggestions")){
                        JsonArray suggestions = jsonObject.getAsJsonArray("suggestions");
                        for (JsonElement suggestionElements: suggestions){
                            JsonObject suggestionObject = suggestionElements.getAsJsonObject();
                            String value = suggestionObject.getAsJsonPrimitive("value").getAsString();
                            JsonObject dataObject = suggestionObject.getAsJsonObject("data");
                            int id = dataObject.getAsJsonPrimitive("id").getAsInt();
                            int baseId = dataObject.getAsJsonPrimitive("base_id").getAsInt();
                            String nombre = dataObject.getAsJsonPrimitive("name").getAsString();
                            String categoria = dataObject.getAsJsonPrimitive("category").getAsString();
                            JsonElement imageElement = dataObject.get("image");
                            String imagen = "";
                            if (imageElement != null && !imageElement.isJsonNull()) {
                                imagen = imageElement.getAsString();
                            }
//                            JsonElement imageThumbnailElement = dataObject.get("image");
//                            String imageThumbnail = null;
//                            if (imageThumbnailElement != null && !imageThumbnailElement.isJsonNull()) {
//                                imageThumbnail = imageThumbnailElement.getAsString();
//                            }
                            ejerciciosResponse.add(new EjercicioResponse(nombre, categoria, imagen));
                        }
                        recyclerAPIAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }

    private void addRow(int[] cantidadSeries, LinearLayout contenedorTexts) {
        cantidadSeries[0]++;

//        Crear un RelativeLayout y su Layout
        RelativeLayout relativeLayout = new RelativeLayout(getContext());
        LinearLayout.LayoutParams paramsRelative = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsRelative.setMargins(26,0,10,0);
        relativeLayout.setLayoutParams(paramsRelative);

//        Crear el Layout de los Texts
        RelativeLayout.LayoutParams paramsSerie = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams paramsRepeticiones = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //        Modificar Layout para colocarlos correctamente
        paramsSerie.addRule(RelativeLayout.CENTER_VERTICAL);
        paramsRepeticiones.addRule(RelativeLayout.ALIGN_PARENT_END);
        paramsRepeticiones.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

//        Crear el EditText de Series
        TextView textSerie = new TextView(getContext());
        textSerie.setLayoutParams(paramsSerie);
        textSerie.setText(""+cantidadSeries[0]);

//        Crear el EditText de Repeticiones
        EditText textRepeticiones = new EditText(getContext());
        textRepeticiones.setLayoutParams(paramsRepeticiones);
        textRepeticiones.setHint("0");
        textRepeticiones.setInputType(InputType.TYPE_CLASS_NUMBER);

//        Añadir a la vista
        relativeLayout.addView(textSerie);
        relativeLayout.addView(textRepeticiones);
        contenedorTexts.addView(relativeLayout);
    }
    private void deleteRow(int[] cantidadSeries, LinearLayout contenedorTexts) {
        if (cantidadSeries[0] > 1){
            cantidadSeries[0]--;
            contenedorTexts.removeViewAt(contenedorTexts.getChildCount()-1);
        }else {
            Toast.makeText(getContext(), "El mínimo de series es 1", Toast.LENGTH_SHORT).show();
        }
    }

    private void createEjercicio(LinearLayout contenedorTexts) {
        StringBuilder repeticiones = new StringBuilder();
        String nombreEjercicio = textEjercicio.getText().toString();
        if (!Validation.validateName(nombreEjercicio)){
            Toast.makeText(getContext(), "Error, Nombre de Ejercicio Vacío", Toast.LENGTH_SHORT).show();
            return;
        }
        int series = 0;
        int items = contenedorTexts.getChildCount();
        for (int j = 1; j < items; j++) {
            RelativeLayout fila = (RelativeLayout) contenedorTexts.getChildAt(j);
            TextView text = (TextView) fila.getChildAt(1);
            if (!Validation.validateName(text.getText().toString())){
                Toast.makeText(getContext(), "Error, Repeticiones Vacías", Toast.LENGTH_SHORT).show();
                return;
            }
            repeticiones.append(text.getText().toString());
            if (j< items -1){
                repeticiones.append(",");
            }
            series++;
        }
        if (ejercicioTemp != null){
            ejercicios.add(new Ejercicio(ejercicioTemp.getNombre(), series, repeticiones.toString(), ejercicioTemp.getImagen()));
            Log.i("imagen", ejercicioTemp.getImagen());
        }
        else {
            ejercicios.add(new Ejercicio(nombreEjercicio, series, repeticiones.toString(), ""));
        }
        recyclerAdapter.notifyDataSetChanged();
    }

    private void createRoutine() throws UnsupportedEncodingException {
        String nombreRutina = textRutina.getText().toString();
        String day = (String) spinner.getSelectedItem();
        if (!Validation.validateName(nombreRutina)){
            Toast.makeText(requireContext(), "Nombre de Rutina Vacío", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ejercicios.size()== 0){
            Toast.makeText(requireContext(), "Mínimo 1 ejercicio", Toast.LENGTH_SHORT).show();
            return;
        }
        RutinaDTO rutina = new RutinaDTO(nombreRutina, ejercicios, day);
        rutinaRepository = new RutinaRepositoryImpl(requireContext());
        rutinaRepository.create(rutina);
//        TODO: Esta forma de volver da fallo en destinationId
        requireActivity().getSupportFragmentManager().popBackStack();
    }

}