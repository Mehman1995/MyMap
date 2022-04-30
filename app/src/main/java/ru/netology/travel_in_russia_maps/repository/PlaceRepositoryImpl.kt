package ru.netology.travel_in_russia_maps.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.netology.travel_in_russia_maps.dao.PlaceDao
import ru.netology.travel_in_russia_maps.dto.Place
import ru.netology.travel_in_russia_maps.entity.PlaceEntity
import ru.netology.travel_in_russia_maps.entity.toDto
import ru.netology.travel_in_russia_maps.error.DbError
import ru.netology.travel_in_russia_maps.error.UnknownError
import java.sql.SQLException


class PlaceRepositoryImpl(private val placeDao: PlaceDao) : PlaceRepository {
    override val data: Flow<List<Place>> = placeDao.getAll()
        .map { it.toDto() }.flowOn(Dispatchers.Default)

    override suspend fun getAll() {
        try {
            placeDao.getAll()
        } catch (e: SQLException) {
            throw DbError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun update(place: Place) {
        try {
            placeDao.update(place.id, place.description, place.name)
        } catch (e: SQLException) {
            throw DbError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun saveDraft(name: String?, description: String?) {
        try {
            placeDao.saveDraft(name, description)
        } catch (e: SQLException) {
            throw DbError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun getDraftName(): String? =
        try {
            placeDao.getDraftName()
        } catch (e: SQLException) {
            throw DbError
        } catch (e: Exception) {
            throw UnknownError
        }


    override suspend fun getDraftDescription(): String? =
        try {
            placeDao.getDraftDescription()
        } catch (e: SQLException) {
            throw DbError
        } catch (e: Exception) {
            throw UnknownError
        }

    override suspend fun visited(id: Long) {
        try {
            placeDao.visited(id)
        } catch (e: SQLException) {
            throw DbError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun notVisited(id: Long) {
        try {
            placeDao.notVisited(id)
        } catch (e: SQLException) {
            throw DbError
        } catch (e: Exception) {
            throw UnknownError
        }
    }


    override suspend fun save(place: Place) {
        try {
            placeDao.insert(PlaceEntity.fromDto(place))
        } catch (e: SQLException) {
            throw DbError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun removeById(id: Long) {
        try {
            placeDao.removeById(id)
        } catch (e: SQLException) {
            throw DbError
        } catch (e: Exception) {
            throw UnknownError
        }
    }


}