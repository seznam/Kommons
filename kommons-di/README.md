# Kodi

Kod is a dependency injection tool for Android application projects written in Kotlin. It is build on Android
components as Application, Activity and Fragment and it builds its dependency tree naturally as the application is build 
by using these components. By default it strictly respects lifecycle of these components, Scopes are automatically 
created in onCreate() and released in onDestroy() callbacks.

## Why?
For some time we've been using [Dagger2](https://google.github.io/dagger/android) as dependency injector and it works well. 
But with every new project we spent a lot of time just with setting it up and defining modules, components, 
subcomponents and dealing with annotation processor errors. After a while it has became really frustrating and we want 
something simpler, readable, with tighter connection to android components. 

Then we found [Koin](https://insert-koin.io/). We loved the simplicity and the Kotlin way of things, but quickly found
that it will not work for our style of work and project setups. Especially the way it defines and uses scopes do not
work for us as we commonly need multiple instances of the same scope and we need it initialize with parameters during
the scope initialization, we don't want to provide parameters for each type injection as it breaks the abstraction.

With these experiences and inspirations in mind, we created this project, which for now serves as a proof of concept.    

## Key features
- very lightweight
- no annotation processor, no extra code generated
- fast project setup without extra boilerplate code
- auto initialization of base Android components as Application, Activity and Fragment
- reusable dependency scopes based on the same scope definition

## Base terminology

#### ScopeDefinition
Definition of one concrete dependency scope. It describes what and how is created.

#### Scope
Instance of scope definition. It contains context and parameters for creating instances and is used for injecting into 
other components. It can have a parent which is used for dependency lookup too.

#### ScopeParameters
Parameters which can be used in Scope for creating instances.

#### factory 
Factory definition creates new instance every time the type is injected.

#### singleton
Singleton definition creates new instance only once for a scope, when the type is injected for the first time. 
Next time it is reused. It is released when the scope is released.

#### weakReference
WeakReference definition creates new instance only once for a scope, when the type is injected for the first time and
holds it as long as external references exist.

## Example
~~~~~~~kotlin
val actScope = activityScope<AccountListActivity> {
    singleton<IAccountManager> { AccountManager(activity) }
    
    factory { PhoneNumberFormatter() }
}

val detailScope = fragmentScope<AccountDetailFragment> {
    singleton { AccountDetailPresenter(obtain()) }
}

class MyApplication {
    fun onCreate() {
        super.onCreate()
        startKodi(actScope, detailScope)
    }
}


class AccountListActivity : AppCompatActivity {
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val accountManager: IAccountManager = obtain()
        showAccountList(accountManager.accounts)
    }
    
    fun showAccountList(accounts: List<Account>) {
        accounts.forEach {
            // addView
            // onClick { showFragment(DetailAccountFragment()) }
        }
    }
}

class AccountDetailPresenter(private val accountManager: IAccountManager, 
                             private val phoneFormatter: PhoneNumberFormatter) {

}

class DetailAccountFragment: Fragment {
    private val presenter by lazy { obtain<AccountDetailPresenter>() }
    
    fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val dataBinding = FragmentAccountDetailBinding.inflate(inflater)
        dataBinding.presenter = presenter
        return dataBinding.root
    }
}
~~~~~~~

    