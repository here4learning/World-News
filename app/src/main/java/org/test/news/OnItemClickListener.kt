package org.test.news

import java.text.FieldPosition

interface OnItemClickListener<T> {
    fun onClick(position: Int,item : T)
}