package ru.netology.travel_in_russia_maps.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.netology.travel_in_russia_maps.entity.DraftEntity
import ru.netology.travel_in_russia_maps.entity.PlaceEntity

@Dao
interface PlaceDao {

    @Query("SELECT * FROM PlaceEntity ORDER BY visited")
    fun getAll(): Flow<List<PlaceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(place: PlaceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(place: List<PlaceEntity>)

    @Query("UPDATE PlaceEntity SET description = :description AND name = :name WHERE id = :id")
    fun update(id: Long, description: String, name: String)

    suspend fun save(place: PlaceEntity) =
        if (place.id == 0L) insert(place) else update(place.id, place.description, place.name)

    @Query("DELETE FROM PlaceEntity WHERE id = :id")
    suspend fun removeById(id: Long)


    @Query("DELETE FROM DraftEntity")
    suspend fun deleteDraft()

    @Insert
    suspend fun insertDraft(draft: DraftEntity?)

    suspend fun saveDraft(name: String?, description: String?) {
        if (name == null && description == null) {
            deleteDraft()
        } else {
            val id = 0L
            val draftEntity = DraftEntity(id = id, name = name, description = description)
            if (draftEntity.id == id) {
                deleteDraft()
                insertDraft(draftEntity)
            } else {
                insertDraft(draftEntity)
            }
        }
    }

    @Query(
        """
           UPDATE PlaceEntity SET
               visited = 1
           WHERE id = :id AND visited = 0;
        """
    )
    suspend fun visited(id: Long)

    @Query(
        """
           UPDATE PlaceEntity SET
               visited = 0
           WHERE id = :id AND visited = 1;
        """
    )
    suspend fun notVisited(id: Long)

    @Query("SELECT name FROM DraftEntity ")
    suspend fun getDraftName(): String?

    @Query("SELECT description FROM DraftEntity ")
    suspend fun getDraftDescription(): String?
}
