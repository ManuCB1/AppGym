package com.example.appgym.control.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appgym.R;

public class NewRoutineFragment extends Fragment {

    private Button btnNewEjercicio;
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

        btnNewEjercicio = view.findViewById(R.id.btnNewEjercicio);
        btnNewEjercicio.setOnClickListener(view1 -> {
            showNewDialog();
        });
    }

    private void showNewDialog() {
        final int[] cantidadSeries = {0};
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_ejercicio, null);
        LinearLayout contenedorTexts = dialogView.findViewById(R.id.contenedorTexts);
        ImageView btnAddRow = dialogView.findViewById(R.id.btnAddRow);
        ImageView btnDeleteRow = dialogView.findViewById(R.id.btnDeleteRow);

        addRow(cantidadSeries, contenedorTexts);

        btnAddRow.setOnClickListener(view -> {
            addRow(cantidadSeries, contenedorTexts);
        });
        btnDeleteRow.setOnClickListener(view -> {
            deleteRow(cantidadSeries, contenedorTexts);
        });

        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(dialogView)
                .setTitle("Crear Ejercicio")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int j = 1; j < contenedorTexts.getChildCount(); j++) {
                            RelativeLayout fila = (RelativeLayout) contenedorTexts.getChildAt(j);
                            TextView text = (TextView) fila.getChildAt(1);
                            Log.i("repeticiones", text.getText().toString());
                        }
                    }
                })
                .setNegativeButton("Cancelar", null)
                .create();
        dialog.show();

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

//        Crear el EditText
        TextView textSerie = new TextView(getContext());
        textSerie.setLayoutParams(paramsSerie);
        textSerie.setText(""+cantidadSeries[0]);

//        Crear el EditText
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
}