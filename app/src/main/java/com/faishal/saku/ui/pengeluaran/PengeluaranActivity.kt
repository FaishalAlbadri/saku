package com.faishal.saku.ui.pengeluaran

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.faishal.saku.R
import com.faishal.saku.adapter.PengeluaranHariAdapter
import com.faishal.saku.adapter.PengeluaranKategoriDanaAdapter
import com.faishal.saku.api.Server
import com.faishal.saku.base.BaseActivity
import com.faishal.saku.data.kategori.dana.KategoriDanaItem
import com.faishal.saku.data.pengeluaran.PengeluaranHariItem
import com.faishal.saku.di.KategoriDanaRepositoryInject
import com.faishal.saku.di.PengeluaranRepositoryInject
import com.faishal.saku.presenter.kategori.dana.KategoriDanaContract
import com.faishal.saku.presenter.kategori.dana.KategoriDanaPresenter
import com.faishal.saku.presenter.pengeluaran.PengeluaranContract
import com.faishal.saku.presenter.pengeluaran.PengeluaranPresenter
import com.faishal.saku.ui.pengeluaran.fragment.AddPengeluaranDialogFragment
import com.faishal.saku.ui.pengeluaran.fragment.EditPengeluaranDialogFragment
import com.faishal.saku.util.SessionManager
import com.faishal.saku.util.Util
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PengeluaranActivity : BaseActivity(), KategoriDanaContract.kategoriDanaView,
    PengeluaranContract.pengeluaranView {

    @BindView(R.id.btn_back)
    lateinit var btnBack: ImageView

    @BindView(R.id.txt_toolbar)
    lateinit var txtToolbar: TextView

    @BindView(R.id.rv_info_pengeluaran)
    lateinit var rvInfoPengeluaran: RecyclerView

    @BindView(R.id.rv_pengeluaran)
    lateinit var rvPengeluaran: RecyclerView

    @BindView(R.id.btn_add)
    lateinit var btnAdd: FloatingActionButton

    @BindView(R.id.refresh_pengeluaran)
    lateinit var refreshPengeluaran: SwipeRefreshLayout

    private lateinit var kategoriDanaPresenter: KategoriDanaPresenter
    private lateinit var pengeluaranKategoriDanaAdapter: PengeluaranKategoriDanaAdapter
    private var kategoriDanaItem: ArrayList<KategoriDanaItem> = ArrayList()

    private lateinit var pengeluaranPresenter: PengeluaranPresenter
    private lateinit var pengeluaranHariAdapter: PengeluaranHariAdapter
    private var pengeluaranHariItem: ArrayList<PengeluaranHariItem> = ArrayList()

    private lateinit var pd: ProgressDialog
    private lateinit var idCatatan: String
    private lateinit var waktu: String
    private lateinit var sessionManager: SessionManager

    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengeluaran)
        ButterKnife.bind(this)

        idCatatan = intent.getStringExtra("id_catatan").toString()
        waktu = intent.getStringExtra("waktu").toString()

        setView()
    }

    private fun setView() {
        txtToolbar.setText("Pengeluaran " + waktu)

        sessionManager = SessionManager(this)

        pengeluaranKategoriDanaAdapter = PengeluaranKategoriDanaAdapter(this, kategoriDanaItem)
        rvInfoPengeluaran.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        rvInfoPengeluaran.setAdapter(pengeluaranKategoriDanaAdapter)

        pengeluaranHariAdapter = PengeluaranHariAdapter(this, pengeluaranHariItem)
        rvPengeluaran.setLayoutManager(LinearLayoutManager(this))
        rvPengeluaran.setAdapter(pengeluaranHariAdapter)

        kategoriDanaPresenter = KategoriDanaPresenter(KategoriDanaRepositoryInject.provideTo(this))
        kategoriDanaPresenter.onAttachView(this)

        pengeluaranPresenter = PengeluaranPresenter(PengeluaranRepositoryInject.provideTo(this))
        pengeluaranPresenter.onAttachView(this)

        kategoriDanaPresenter.kategoriDana(sessionManager.getIdUser().toString(), idCatatan)

        fragmentManager = supportFragmentManager

        Util.refreshColor(refreshPengeluaran)
        refreshPengeluaran.setOnRefreshListener {
            kategoriDanaPresenter.kategoriDana(sessionManager.getIdUser().toString(), idCatatan)
        }

        pd = ProgressDialog(this)
        pd.setCancelable(false)
        pd.setCanceledOnTouchOutside(false)
        pd.setMessage("Loading")
        pd.show()
    }

    fun showDeleteDialog(idPengeluaran: String, descPengeluaran: String) {
        AlertDialog.Builder(this, R.style.AlertDialogTheme)
            .setTitle("Hapus Pengeluaran")
            .setMessage("Apakah anda yakin ingin menghapus \n\"" + descPengeluaran + "\" ?")
            .setPositiveButton("Hapus") { dialogInterface, i ->
                pengeluaranPresenter.deletePengeluaran(idPengeluaran)
                pd.show()
            }
            .setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            .show()
    }

    fun showEditDialog(idPengeluaran: String, nominalPengeluaran: String, descPengeluaran: String) {
        EditPengeluaranDialogFragment.newInstance(
            this,
            idPengeluaran,
            nominalPengeluaran,
            descPengeluaran
        )
            .show(fragmentManager, "")
    }

    fun editPengeluaran(
        idPengeluaran: String,
        nominalPengeluaran: String,
        descPengeluaran: String
    ) {
        pd.show()
        pengeluaranPresenter.editPengeluaran(idPengeluaran, descPengeluaran, nominalPengeluaran)
    }

    fun addPengeluaran(
        idKategoriDana: String,
        idKategoriPokok: String,
        nominalPengeluaran: String,
        descPengeluaran: String
    ) {
        pd.show()
        pengeluaranPresenter.addPengeluaran(
            sessionManager.getIdUser().toString(),
            idCatatan,
            idKategoriDana,
            idKategoriPokok,
            descPengeluaran,
            nominalPengeluaran
        )
    }

    override fun onSuccessKategoriDana(danaListItem: List<KategoriDanaItem>, msg: String) {
        pengeluaranKategoriDanaAdapter.delete()
        kategoriDanaItem.clear()
        kategoriDanaItem.addAll(danaListItem)
        pengeluaranKategoriDanaAdapter.notifyDataSetChanged()
        pengeluaranPresenter.pengeluaran(sessionManager.getIdUser().toString(), idCatatan)
    }

    override fun onErrorKategoriDana(msg: String) {
        if (msg.equals(Server.CHECK_INTERNET_CONNECTION)) {
            kategoriDanaPresenter.kategoriDana(sessionManager.getIdUser().toString(), idCatatan)
        } else {
            refreshPengeluaran.setRefreshing(false)
            pd.cancel()
        }
    }

    override fun onSuccessPengeluaran(pengeluaranListItem: List<PengeluaranHariItem>, msg: String) {
        refreshPengeluaran.setRefreshing(false)
        pd.cancel()
        pengeluaranHariAdapter.delete()
        pengeluaranHariItem.clear()
        pengeluaranHariItem.addAll(pengeluaranListItem)
        pengeluaranHariAdapter.notifyDataSetChanged()
    }

    override fun onErrorPengeluaran(msg: String) {
        if (msg.equals(Server.CHECK_INTERNET_CONNECTION)) {
            pengeluaranPresenter.pengeluaran(sessionManager.getIdUser().toString(), idCatatan)
        } else {
            refreshPengeluaran.setRefreshing(false)
            pd.cancel()
        }
    }

    override fun onSuccessAddPengeluaran(msg: String) {
        kategoriDanaPresenter.kategoriDana(sessionManager.getIdUser().toString(), idCatatan)
    }

    override fun onErrorAddPengeluaran(msg: String) {
        refreshPengeluaran.setRefreshing(false)
        pd.cancel()

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onSuccessEditPengeluaran(msg: String) {
        kategoriDanaPresenter.kategoriDana(sessionManager.getIdUser().toString(), idCatatan)
    }

    override fun onErrorEditPengeluaran(msg: String) {
        refreshPengeluaran.setRefreshing(false)
        pd.cancel()

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onSuccessDeletePengeluaran(msg: String) {
        kategoriDanaPresenter.kategoriDana(sessionManager.getIdUser().toString(), idCatatan)
    }

    override fun onErrorDeletePengeluaran(msg: String) {
        refreshPengeluaran.setRefreshing(false)
        pd.cancel()

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    @OnClick(R.id.btn_back)
    fun onBtnBackClicked() {
        onBackPressed()
    }

    @OnClick(R.id.btn_add)
    fun onBtnAddClicked() {
        AddPengeluaranDialogFragment.newInstance(this).show(fragmentManager, "")
    }
}