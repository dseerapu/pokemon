# Pokemon App (Clean Architecture + Jetpack Compose)

A modular Android application built using Jetpack Compose, Clean Architecture, Hilt Dependency Injection, and Paging 3, following best practices for scalability and testability.

### Architecture Overview
The app follows Clean Architecture and Uni-Directional Data Flow ( UDF)
```
UI (Compose)
   ↓
ViewModel (State management)
   ↓
UseCase (Business logic)
   ↓
Repository (Abstract contract)
   ↓
RepositoryImpl (Data + Paging3)
   ↓
PokemonApi (Network - Retrofit + Okhttp)
```
* Each layer depends only on the layer below it, promoting testability and separation of concerns.


# Features
* **Pokemon List** — Paginated list of Pokemon with search support
* **Pokemon Detail** — Detailed screen showing Pokemon stats, types, abilities
* **Clean Architecture** — Domain-driven design with proper layer separation
* **Modularization** — Independent feature modules with isolated dependencies
* **Hilt for DI** — Scoped dependencies and feature-level navigator injection
* **Navigation Component** — Feature-based navigation handled via interfaces
* **Paging 3** — Efficient and seamless data loading with caching
* **Jetpack Compose UI** — Fully declarative, state-driven UI

## Module Responsibilities
### 1. :app
#### Responsibility
* Initializes the main PokemonApp() composable.
* Hosts the NavHostController that wires together feature-level navigations.
* Provides app-level dependency graph for Hilt.
* Composes the top-level navigation graph combining:
* Pokemon List Navigation (feature_pokemon_list)
* Pokemon Detail Navigation (feature_pokemon_detail)


### 2. :data
#### Responsibility: 
* Implements the data sources and repository logic.
* Implements PokemonRepositoryImpl, which fulfills PokemonRepository
* Handles networking (via Retrofit)
* Applies data mapping between API DTOs and domain models
* Implements Paging 3 for efficient, incremental loading
* Optionally can add caching, local database, or Room later

#### Paging 3 Integration:
The repository exposes a Pager that emits PagingData<Pokemon>, making pagination automatic and efficient.

### 3. :core:network

#### Responsibilities
* Centralized networking configuration
* Provides Retrofit, OkHttp, and Caching
* Shared across all data modules
* Check for ```Network Module.kt```
* Uses on-disk caching via Cache(context.cacheDir, 10MB)
* Cached responses are served when network is slow or offline

### 4. :feature:pokemon-list
#### Responsibility: 
Displays a paginated list of Pokemon and supports search.

##### ViewModel

* Injects GetPokemonListUseCase and SearchPokemonUseCase
* Collects paginated Pokemon data from repository
* Handles UI states using ```PokemonListUiState```:
```
- Loading
- Success
- Error
```
* Supports local in-memory search without refetching network data.

##### UI

* Displays paginated list via LazyColumn
* Includes a SearchBar with progress indicator when searching
* Uses PagingDataAdapter (Paging Compose)

### 5. :feature:pokemon-detail
#### Responsibility: 
Displays detailed information about a selected Pokemon.

##### ViewModel

* Injects ```GetPokemonDetailUseCase```
* Fetches Pokemon details using the provided ```pokemonId```
* Emits states via ```PokemonDetailUiState```:
```
- Loading
- Success(Pokemon)
- Error(Message)
```
* Supports local in-memory search without refetching network data.

##### UI

* Displays Pokemon image, stats, types, abilities, and physical data

### State Management
* Each feature uses its own UiState sealed class pattern:
```
sealed class PokemonListUiState {
    object Loading : PokemonListUiState()
    data class Success(val pokemon: List<Pokemon>) : PokemonListUiState()
    data class Error(val message: String) : PokemonListUiState()
}
```
* UI reacts to state changes via collectAsState() from ViewModel.


