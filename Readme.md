# KASH 

Toolset for android developers that contains base features for common tasks.

## Getting Started

The project is divided to different modules:
* [Toolset](#toolset) - features for make ur own Components that works and manages in Android Service (same process as ur app, and same thread). Presetes for networks, database and shared preferences tasks.
* [UI](#ui) - contains MVVM (Android Components) presets. Navigation (drawer layout) preset to manage side menu. Adapter Delegation preset to make listview more flexible on different types of items. Utils and etc.
* [sample] - contains example of features from [toolset] and [ui] package based on Reddit browsing.
* [ipc](#ipc) - using android.os.Messenger and Observer pattern to observe data changes between different processes.
* [ipc_sample] - sample of ipc with Location Tracking Service.

### TOOLSET
There is an android service represented in 
```kotlin
com.axmor.kash.toolset.service.CompositeService
```
which delegates service logic to
```kotlin
com.axmor.kash.toolset.service.ServiceCore
```
It contains client components and manages them by certain lifeline.
Default lifeline: When there first bind to service the core activates all components.
When we lost binds on the service there 5 minutes before all components will be deactivated and the service will be closed.
This time is interrupted if we have new binds before end.

To create and add new components (smth u wont to executing in androidservice) u should extend it by
```kotlin
com.axmor.kash.toolset.service.interfaces.Component 
```
and add in overrided buildComposite() method of ur implementation of CompositeService.
See example:
```kotlin
com.axmor.kash.sample.core.AppService
```
If u want to get components from service u should use 
```kotlin
com.axmor.kash.toolset.service.connection.CompositeServiceConnection
```
See example:
```kotlin
com.axmor.kash.ui.mvvm.KashViewModel
```
There already 3 components for work with network (through Retrofit), database (through Room) and Repository that can contain both of network and database components. They all are used in [sample].
```kotlin
com.axmor.kash.toolset.local.db.service.KashDatabaseService
com.axmor.kash.toolset.network.KashNetworkApiService
com.axmor.kash.toolset.repository.KashEntityRepositoryService
```
The database component works with 
```kotlin
com.axmor.kash.toolset.local.db.KashBaseDao
com.axmor.kash.toolset.local.db.KashDatabase
```
so u need to extends ur own from those classes.

There is a couple of public methods to work with SharedPreferences. It allows to put and get get primitives, Strings and Object(using json by Moshi).
```kotlin
com.axmor.kash.toolset.local.pref.SharedPreferencesHelper
```
Utils to work with dates and json in package
```kotlin
com.axmor.kash.toolset.utils
```
### UI
##### Package 'mvvm'
There are a couple of classes that represents mvvm (using Android Components) in 'mvvm' package and uses
services from [toolset] module.
```kotlin
com.axmor.kash.ui.mvvm.KashViewModel
```
It implements 
```kotlin
com.axmor.kash.toolset.service.connection.ConnectionNode
```
and connects to service through 
```kotlin
com.axmor.kash.toolset.service.connection.CompositeServiceConnection
```
It also contains 
```kotlin
com.axmor.kash.ui.progress.ProgressLiveData
com.axmor.kash.ui.error.ErrorLiveData
```
to inform subscribers about showing/hiding of progress and errors.
U can see example of usage in [sample]:
```kotlin
com.axmor.kash.sample.ui.reddit_list.RedditListViewModel
com.axmor.kash.sample.ui.favorites.FavoritesListViewModel
```
KashActivity and KashFragment have same implementation. They connects to view model and subscribes on error and progress live data.
```kotlin
com.axmor.kash.ui.mvvm.KashActivity
com.axmor.kash.ui.mvvm.KashFragment
```
##### Package 'adapters'
Contains logic to make flexible adapter that can have different types of data and allow u easily manages this types between different adapters (uses RecyclerView.Adapter as base).
```kotlin
com.axmor.kash.ui.adapters.KashAdapterDelegate
```
U need to extend ur own delegate by this where determine view holder and for wich type of data it is used.
See example:
```kotlin
com.axmor.kash.sample.ui.adapter.news_list.RedditNewsAdapterDelegate
сom.axmor.kash.sample.ui.adapter.news_list.RedditNewsLoadingAdapterDelegate
```
```kotlin
com.axmor.kash.ui.adapters.KashBaseAdapter
```
KashBaseAdapter contains data and KashAdapterDelegatesManager to manage different delegates.
U can use this adapter or extend ur own.
See example:
```kotlin
com.axmor.kash.sample.ui.adapter.news_list.RedditNewsAdapter
```
##### Package 'navigation'
Contains base logic to create navigation items for drawer layout.
First of all u need to extend
```kotlin
com.axmor.kash.ui.navigation.NavigatableScreenEnv
```
See example:
```kotlin
com.axmor.kash.sample.ui.main.MainMenuActivity
```
To detect when item (which represents fragment openning) is activated.
This activity also contains
```kotlin
com.axmor.kash.ui.navigation.NavigationScene
```
which u configures and where adds navigation items.
NavigationScene class manages items and activates them when they are clicked.
There already two items that represent Action Item (when ur want some action on click) and Fragment Item (when ur want to open fragment on click).
```kotlin
com.axmor.kash.ui.navigation.NavigationItemAction
com.axmor.kash.ui.navigation.NavigationItemRootFragment
```
##### Package 'commons'
There only one class that detects when we arrived to end of the list view.
```kotlin
com.axmor.kash.ui.commons.InfiniteScrollListener
```
See example:
```kotlin
com.axmor.kash.sample.ui.reddit_list.RedditListFragment
```
##### Package 'utils'
Plenty of common utils.
```kotlin
com.axmor.kash.ui.utils.ActivityUtils
```
Works with activity manager.
```kotlin
com.axmor.kash.ui.utils.ImageViewExtensions
```
Loading images using picasso.
```kotlin
com.axmor.kash.ui.utils.MetricUtils
```
Transfering dp to px.
```kotlin
com.axmor.kash.ui.utils.NetworkUtils
```
Detecting network connection.
```kotlin
com.axmor.kash.ui.utils.OSUtils
```
Detecting os version.
```kotlin
com.axmor.kash.ui.utils.PackageInfoUtils
```
Getting information about current package.
```kotlin
com.axmor.kash.ui.utils.TextUtils
```
Formatting text with html.
```kotlin
com.axmor.kash.ui.utils.ViewGroupExtension
```
Inflating views.

### IPC
Ipc using Observer pattern and wraps over android.os.Messenger.
See example in [ipc_sample] module.

First of all u should bind to Service.
On the service side create Messenger and set Handler to this one.
Get binder from created Messenger and send it back in onBinde().
See example in:
```kotlin
com.axmor.ipc_sample.tracking.ipc.TrackingServiceStub
```
When u get binder on client side, create Messenger with one.
Now u can send message to Service and handle them in Handler u created before.
To make observable on some data create:
```kotlin
val observableClient = IPCObservableClient<TrackingService.PublicState>(callbackLooper)
```
And send it throw messenger to service.
On ther service side, when u get the message, create:
```kotlin
var publicState: IPCValueObservable<PublicState> = IPCValueObservable(PublicState(State.IDLE))
val clientProxy = IPCObservableClientProxy<TrackingService.PublicState>(msg.replyTo)
val observableWrapper = IPCObservableWrapper(publicState, looper)
val clientProxy.onObservableCreated(observableWrapper)
```
Not u can addObserver to (on Client side):
```kotlin
val observableClient = IPCObservableClient<TrackingService.PublicState>(callbackLooper)
observableClient.addObserver(object : IPCObserver<TrackingService.PublicState> {
            override fun onChanged(value: TrackingService.PublicState) {
                var s = value.state
            }
        })
```
and change data (on Service side):
```kotlin
var publicState: IPCValueObservable<PublicState> = IPCValueObservable(PublicState(State.IDLE))
publicState.setValue(PublicState(state))
```

## Built With
* [Kotlin](https://kotlinlang.org/) - Base language of project.
* [Appcompat v7 27.0.0](https://developer.android.com/topic/libraries/support-library/packages.html) - Uses activity that implements Lifecycle part of android components.
* [Rxandroid and Rxjava](https://github.com/ReactiveX/RxJava) - Calls to Service's Components.
* [Retrofit2 and RetrofitRxjava](http://square.github.io/retrofit/) - Network Calls.
* [Moshi](https://github.com/square/moshi) - Json serialization/deserialization.
* [Lifecycle Extensions](https://developer.android.com/topic/libraries/architecture/adding-components.html) - Android Components.
* [Room](https://developer.android.com/topic/libraries/architecture/room.html) - Library to work with SQLLite by Google.
* [Recycler View](https://developer.android.com/training/material/lists-cards.html) - Modern library to present lists of view.
* [Picasso](http://square.github.io/picasso/) - Library to async loading images from network.

