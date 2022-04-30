package ru.netology.travel_in_russia_maps.utils

import android.view.View
import androidx.constraintlayout.widget.Group

fun Group.addAllOnClickListener(listener: View.OnClickListener?) {
    referencedIds.forEach { id ->
        rootView.findViewById<View>(id).setOnClickListener(listener)
    }
}