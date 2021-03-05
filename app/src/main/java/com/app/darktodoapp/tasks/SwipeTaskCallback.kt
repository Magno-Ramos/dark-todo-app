package com.app.darktodoapp.tasks

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.app.darktodoapp.R
import com.app.darktodoapp.helper.getCompatColor
import com.app.darktodoapp.helper.getCompatDrawable
import com.app.sdk.models.Task

abstract class SwipeTaskCallback(context: Context) : ItemTouchHelper.Callback() {

    private val trashIcon = context.getCompatDrawable(R.drawable.ic_delete_white_24)
    private val checkIcon = context.getCompatDrawable(R.drawable.ic_round_check_box_white_24)

    private val redColor = context.getCompatColor(R.color.colorDanger)
    private val primaryColor = context.getCompatColor(R.color.colorAccent)

    abstract fun getTask(position: Int): Task

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val task = getTask(viewHolder.adapterPosition)
        var swipeFlags = ItemTouchHelper.LEFT
        if (task.complete.not()) swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return makeMovementFlags(0, swipeFlags)
    }

    override fun getAnimationDuration(
        recyclerView: RecyclerView,
        animationType: Int,
        animateDx: Float,
        animateDy: Float
    ): Long = 250L

    override fun onChildDraw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val direction = findDirection(dX)

        val swipeItem: SwipeBackgroundItem? = when (direction) {
            ItemTouchHelper.LEFT -> SwipeBackgroundItem(trashIcon, redColor)
            ItemTouchHelper.RIGHT -> SwipeBackgroundItem(checkIcon, primaryColor)
            else -> null
        }

        swipeItem?.config(viewHolder.itemView, direction, dX)
        swipeItem?.shapeBackground?.draw(canvas)

        val clipLeft: Int
        val clipRight: Int

        when (direction) {
            ItemTouchHelper.RIGHT -> {
                clipLeft = itemView.left
                clipRight = itemView.left + dX.toInt()
            }
            ItemTouchHelper.LEFT -> {
                clipLeft = itemView.right + dX.toInt()
                clipRight = itemView.right
            }
            else -> {
                clipLeft = dX.toInt()
                clipRight = dX.toInt()
            }
        }

        canvas.clipRect(clipLeft, itemView.top, clipRight, itemView.bottom)

        swipeItem?.icon?.draw(canvas)
        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun findDirection(dx: Float) = when {
        dx > 0f -> ItemTouchHelper.RIGHT
        dx < 0f -> ItemTouchHelper.LEFT
        else -> -1
    }

    private class SwipeBackgroundItem(
        var icon: Drawable?,
        var backgroundColor: Int
    ) {

        var shapeBackground: ShapeDrawable? = null

        fun config(itemView: View, direction: Int, dx: Float) {
            val iconMargin: Int = (itemView.height - icon!!.intrinsicHeight) / 2
            val iconTop: Int = itemView.top + (itemView.height - icon!!.intrinsicHeight) / 2
            val iconBottom: Int = iconTop + icon!!.intrinsicHeight

            var iconLeft = 0
            var iconRight = 0

            var backgroundLeft = 0
            var backgroundRight = 0

            var shapeRadius = floatArrayOf()

            when (direction) {
                ItemTouchHelper.RIGHT -> {
                    iconLeft = itemView.left + iconMargin
                    iconRight = itemView.left + iconMargin + icon!!.intrinsicWidth

                    backgroundLeft = itemView.left
                    backgroundRight = itemView.left + dx.toInt()

                    shapeRadius = buildRadius(
                        topLeft = 8,
                        topRight = 0,
                        bottomRight = 0,
                        bottomLeft = 8
                    )
                }
                ItemTouchHelper.LEFT -> {
                    iconLeft = itemView.right - iconMargin - icon!!.intrinsicWidth
                    iconRight = itemView.right - iconMargin

                    backgroundLeft = itemView.right + dx.toInt()
                    backgroundRight = itemView.right

                    shapeRadius = buildRadius(
                        topLeft = 0,
                        topRight = 8,
                        bottomRight = 8,
                        bottomLeft = 0
                    )
                }
            }

            val background = RoundRectShape(shapeRadius, null, null)
            shapeBackground = ShapeDrawable(background)
            shapeBackground?.apply {
                setBounds(
                    backgroundLeft,
                    itemView.top,
                    backgroundRight,
                    itemView.bottom
                )
                paint.color = backgroundColor
            }

            icon!!.setBounds(iconLeft, iconTop, iconRight, iconBottom)
        }

        private fun buildRadius(topLeft: Int, topRight: Int, bottomRight: Int, bottomLeft: Int) =
            floatArrayOf(
                topLeft.toFloat(), topLeft.toFloat(),
                topRight.toFloat(), topRight.toFloat(),
                bottomRight.toFloat(), bottomRight.toFloat(),
                bottomLeft.toFloat(), bottomLeft.toFloat()
            )
    }
}

