package com.example.androiddevchallenge.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.data.Weather
import com.example.androiddevchallenge.repository.CitiesRepository
import com.example.androiddevchallenge.repository.ProfileRepository
import com.example.androiddevchallenge.repository.WeatherRepository
import com.meowbase.toolkit.coroutines.launchIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 用于管理主页屏幕的数据
 * The ViewModel of home screen.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@HiltViewModel
class ViewModel @Inject constructor(
  private val repository: WeatherRepository,
  private val citiesRepository: CitiesRepository,
  private val profileRepository: ProfileRepository,
) : ViewModel() {
  private val _allCityWeathers = MutableLiveData(
    listOf(repository.getByLocation())
      + citiesRepository.getAllAdd().map(repository::getByCity)
  )
  private val _currentWeatherIndex = MutableLiveData(0)
  private val _avatar = MutableLiveData(profileRepository.getAvatar())

  /** Read only */

  val currentWeather: Weather get() = _allCityWeathers.value!![_currentWeatherIndex.value!!]

  val allCityWeathers: LiveData<List<Weather>> = _allCityWeathers
  val currentWeatherIndex: LiveData<Int> = _currentWeatherIndex
  val avatar: LiveData<Any> = _avatar

  /**
   * 选择城市的天气
   *
   * @param index 城市页的下标
   */
  fun selectCityWeather(index: Int) {
    _currentWeatherIndex.value = index
  }
}
