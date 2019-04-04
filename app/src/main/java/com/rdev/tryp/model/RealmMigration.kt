package com.rdev.tryp.model

import android.util.Log
import io.realm.DynamicRealm
import io.realm.RealmMigration
import io.realm.RealmSchema

/**
 * Created by Andrey Berezhnoi on 14.03.2019.
 */


class RealmMigration: RealmMigration{
    private val TAG = "RealmMigration"
    private var version: Int = 0

    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        Log.e(TAG, "realm migration, oldVersion: $oldVersion, newVersion: $newVersion")
        val schema = realm.schema
        version = oldVersion.toInt()

        while(version < newVersion){
            updateMigration(schema)
        }
    }

    private fun updateMigration(schema: RealmSchema){
        Log.e(TAG, "updateMigration from version: $version to actual")

        version++
    }

    override fun hashCode(): Int {
        return 37
    }

    override fun equals(other: Any?): Boolean {
        return other is com.rdev.tryp.model.RealmMigration
    }
}