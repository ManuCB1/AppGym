package com.example.appgym.control.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
        Button btnAddRow = dialogView.findViewById(R.id.btnAddRow);
        Button btnDeleteRow = dialogView.findViewById(R.id.btnDeleteRow);

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

                    }
                })
                .setNegativeButton("Cancelar", null)
                .create()
                .show();

    }

    private void addRow(int[] cantidadSeries, LinearLayout contenedorTexts) {
        cantidadSeries[0]++;
        EditText editText = new EditText(getContext());
        editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        editText.setHint("Repeticiones para Serie " + cantidadSeries[0]);
        contenedorTexts.addView(editText);
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