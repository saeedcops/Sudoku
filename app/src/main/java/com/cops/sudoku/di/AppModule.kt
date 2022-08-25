package com.cops.sudoku.di
import android.app.Application
import android.content.Context
import androidx.room.Room
import com.cops.sudoku.MainApp
import com.cops.sudoku.data.data_source.SudokuDatabase
import com.cops.sudoku.data.repository.GameRepositoryImpl
import com.cops.sudoku.data.repository.SettingRepositoryImpl
import com.cops.sudoku.data.repository.StatisticRepositoryImpl
import com.cops.sudoku.domain.model.Setting
import com.cops.sudoku.domain.repository.IGameRepository
import com.cops.sudoku.domain.repository.ISettingRepository
import com.cops.sudoku.domain.repository.IStatisticRepository
import com.cops.sudoku.domain.use_case.game.*
import com.cops.sudoku.domain.use_case.setting.GetSetting
import com.cops.sudoku.domain.use_case.setting.InsertSetting
import com.cops.sudoku.domain.use_case.setting.SettingUseCases
import com.cops.sudoku.domain.use_case.statistic.GetStatistics
import com.cops.sudoku.domain.use_case.statistic.InsertStatistic
import com.cops.sudoku.domain.use_case.statistic.StatisticUseCases
import com.cops.sudoku.domain.util.SudokuPuzzle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMainApp(@ApplicationContext app: Context): MainApp {
        return app as MainApp
    }


    @Provides
    @Singleton
    fun provideSudokuDatabase(application: Application):SudokuDatabase{

        return Room.databaseBuilder(
            application,
            SudokuDatabase::class.java,
            SudokuDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideStatisticRepository(database: SudokuDatabase):IStatisticRepository{

        return StatisticRepositoryImpl(database.statisticsDoa)
    }

    @Provides
    @Singleton
    fun provideSettingRepository(database: SudokuDatabase):ISettingRepository{

        return SettingRepositoryImpl(database.settingDoa)
    }

    @Provides
    @Singleton
    fun provideGameRepository(database: SudokuDatabase):IGameRepository{

        return GameRepositoryImpl(database.sudokuDoa)
    }

    @Provides
    fun provideSudokuPuzzle(): SudokuPuzzle {
        return SudokuPuzzle()
    }

    @Provides
    @Singleton
    fun provideSetting():Setting{

        return Setting()
    }

    @Provides
    @Singleton
    fun provideSettingUseCases(repository:ISettingRepository):SettingUseCases{

        return SettingUseCases(
            InsertSetting(repository),
            GetSetting(repository),
        )
    }

    @Provides
    @Singleton
    fun provideStatisticUseCases(repository:IStatisticRepository):StatisticUseCases{

        return StatisticUseCases(
            InsertStatistic(repository),
            GetStatistics(repository),
            )
    }

    @Provides
    @Singleton
    fun provideGameUseCases(gameRepository:IGameRepository): GameUseCases {

        return GameUseCases(
            CreateGame(),
            SelectNode(),
            UpdateNode(),
            EraseNode(),
            Hint(),
            Pencil(),
            Fast(),
            Undo(),
            UpdateSudoku(gameRepository),
            GetSudoku(gameRepository),
            DeleteSudoku(gameRepository),
        )
    }
}