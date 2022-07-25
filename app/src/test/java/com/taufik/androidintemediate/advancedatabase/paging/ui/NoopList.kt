package com.taufik.androidintemediate.advancedatabase.paging.ui

import androidx.recyclerview.widget.ListUpdateCallback

object NoopList {
    val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }
}