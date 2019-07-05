package com.haku.excalibur.config

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.haku.excalibur.model.Scripts
import com.haku.excalibur.utils.ADBUtils
import com.haku.excalibur.utils.SpUtils
import com.haku.excalibur.utils.recyclerView
import org.jetbrains.anko.button
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import org.jetbrains.anko.verticalLayout
import org.jetbrains.anko.wrapContent


/**
 * Usage:
 *
 * Created by HaKu on 2019-06-24.
 */
class ConfigActivity : AppCompatActivity() {

    private var mScript = SpUtils.scripts
    private lateinit var mAdapter: RecyclerView.Adapter<EmptyViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        verticalLayout {
            button("新增配置") {
                setOnClickListener {
                    val intent = Intent(context, EditActivity::class.java)
                    startActivity(intent)
                }
            }
            recyclerView {
                layoutManager = LinearLayoutManager(context)
                mAdapter = object : RecyclerView.Adapter<EmptyViewHolder>() {
                    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmptyViewHolder {
                        val view = ConfigItemView(context)
                        view.layoutParams = FrameLayout.LayoutParams(matchParent, wrapContent)
                        return EmptyViewHolder(view)
                    }

                    override fun getItemCount(): Int {
                        return mScript.items.count()
                    }

                    override fun onBindViewHolder(holder: EmptyViewHolder, position: Int) {
                        if (holder.itemView is ConfigItemView) {
                            holder.itemView.setData(mScript.items[position])
                            holder.itemView.setOnClick({
                                val intent = Intent(context, EditActivity::class.java)
                                intent.putExtra("pos", position)
                                startActivity(intent)
                            }, {
                                ADBUtils.curCmd = mScript.items[position]
                                Toast.makeText(context, "使用该脚本", Toast.LENGTH_SHORT).show()
                            }, {
                                Log.e("HaKu", "delete: $position")
                                mScript.items.remove(mScript.items[position])
                                SpUtils.scripts = Scripts(mScript.items)
                                mAdapter.notifyDataSetChanged()
                            })
                        }
                    }
                }
                adapter = mAdapter
            }
        }

    }

    override fun onResume() {
        super.onResume()
        mScript = SpUtils.scripts
        mAdapter.notifyDataSetChanged()
    }
}


class EmptyViewHolder(emptyView: View) : RecyclerView.ViewHolder(emptyView)