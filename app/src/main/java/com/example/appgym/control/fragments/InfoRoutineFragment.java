package com.example.appgym.control.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.appgym.R;
import com.example.appgym.adapter.RecyclerInfoRoutineAdapter;
import com.example.appgym.model.PopupListener;
import com.example.appgym.model.Rutina;
import com.example.appgym.model.TaskCompleted;
import com.example.appgym.repository.RutinaRepositoryImpl;
import com.example.appgym.utils.Constantes;
import com.example.appgym.utils.Utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class InfoRoutineFragment extends BaseFragment implements PopupListener {

    private RecyclerView recycler;
    private RecyclerInfoRoutineAdapter adapter;
    private RutinaRepositoryImpl rutinaRepository;
    private List<Rutina> rutinas;
    private static final String argParam1 = Constantes.ARG_RUTINA;

    private int title = R.string.title_info_routine;
    private int menu = 0;
    public InfoRoutineFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info_routine, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycler = view.findViewById(R.id.recyclerInfoRoutine);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));

        setMenu(getString(title), menu);
        getRutinas();
    }

    private void getRutinas() {
        try {
            rutinaRepository = new RutinaRepositoryImpl(getContext());
            rutinas = new ArrayList<>();
            rutinaRepository.getAll(new TaskCompleted<List<Rutina>>() {
                @Override
                public void onTaskCompleted(List<Rutina> s) {
                    rutinas.addAll(s);
                    loadRecycler();
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void loadRecycler() {
        adapter = new RecyclerInfoRoutineAdapter(rutinas, Constantes.MENU_INFO_ROUTINE, this);
        recycler.setAdapter(adapter);
    }

    @Override
    public void selectedItem(int position, MenuItem item) {
        String titleDialog = "Eliminar";
        String messageDialog = "¿Borrar la rutina " + rutinas.get(position).getNombre()+ "?";

        if (item.getItemId() == R.id.titleInfo){
            sendRoutine(position, R.id.action_infoRoutineFragment_to_detailFragment);
        }
        if (item.getItemId() == R.id.titleHistorial){
            sendRoutine(position, R.id.action_infoRoutineFragment_to_historialFragment);
        }
        if (item.getItemId() == R.id.titleRemove){
            Utils.showDialogDelete(requireContext(), titleDialog, messageDialog,
                    (dialogInterface, i) -> {
                        rutinaRepository = new RutinaRepositoryImpl(requireContext());
                        rutinaRepository.delete(rutinas.get(position).getId());
                        getRutinas();
                        adapter.notifyDataSetChanged();
                        },null);
        }
    }

    private void sendRoutine(int position, int menu) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(argParam1, rutinas.get(position));
        Navigation.findNavController(getView()).navigate(menu, bundle);
    }

}