package com.zxp.data

/**
 * Created by 任小龙 on 2020/7/15.
 */
data class DemoKotlinInfo(
    val code: Int,
    val datas: List<DemoData>,
    val msg: String,
    val status: String
)

data class DemoData(
    val author: String,
    val avatar: String,
    val bookmark: Any,
    val category: String,
    val comment: Any,
    val create_time: String,
    val description: String,
    val excerpt: String,
    val fm: String,
    val fm_play: String,
    val good: Any,
    val hot_comments: List<HotComment>,
    val html5: String,
    val id: String,
    val lead: String,
    val link_url: String,
    val lunar_type: String,
    val model: Any,
    val name: String,
    val nickname: String,
    val parseXML: Int,
    val position: String,
    val publish_time: String,
    val share: String,
    val show_uid: String,
    val status: String,
    val tags: List<Tag>,
    val thumbnail: String,
    val title: String,
    val total_url: String,
    val tpl: Int,
    val uid: String,
    val update_time: String,
    val video: String,
    val view: Any
)

data class HotComment(
    val avatar: String,
    val content: String,
    val good: String,
    val id: String,
    val model: String,
    val nickname: String,
    val pid: String,
    val post_id: String,
    val to_author_name: String,
    val uid: String,
    val under_id: String,
    val update_time: String
)

data class Tag(
    val name: String
)