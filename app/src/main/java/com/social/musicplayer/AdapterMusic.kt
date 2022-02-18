import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.social.musicplayer.Music
import com.social.musicplayer.R
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_music.view.*

class AdapterMusic : RecyclerView.Adapter<AdapterMusic.MusicViewHolder> {
    private var musics: MutableList<Music>
    var listener: IMusic

    constructor(
        music: MutableList<Music>,
        listener: IMusic
    ) {
        this.musics = music
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_music,

            parent, false
        )
        return MusicViewHolder(itemView)
    }

    private fun setImage(civ: CircleImageView, uri: Uri?) {
        Glide.with(civ.context)
            .load(uri)
            .apply(
                RequestOptions.placeholderOf(R.drawable.img_avatar)
                    .error(R.drawable.img_avatar)
            )
            .into(civ)
    }


    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val currentItem = musics[position]
        setImage(holder.civ, currentItem.uriImage)
        holder.textView1.text = currentItem.title
        holder.textView2.text = currentItem.author
    }

    override fun getItemCount() = musics.size

    inner class MusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val civ: CircleImageView = itemView.image_view
        val textView1: TextView = itemView.text_view_1
        val textView2: TextView = itemView.text_view_2

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            listener.onItemClick(layoutPosition)
        }
    }

    interface IMusic {
        fun onItemClick(position: Int)
    }
}