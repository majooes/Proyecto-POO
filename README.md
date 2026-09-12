# Mutant Battle - Especificación de Clases

## Capa Modelo (`model`)

### Clase `Mutante`
**Atributos:**
- `nombre: String`
- `energia: int` — inicia en 100
- `capacidadDefensa: int` — valor entre 1 y 3
- `poder: PoderMutante` — máximo un poder por mutante
- `equipo: Equipo`
- `posicionX: double`, `posicionY: double`
- `velocidad: double`
- `vivo: boolean`

**Métodos:**
- `mover(): void`
- `atacar(Mutante objetivo): void`
- `defenderse(): void`
- `recibirDano(int dano): void`
- `estaVivo(): boolean`
- `getEnergia(): int`

### Clase `PoderMutante`
**Atributos:**
- `nombre: String`
- `capacidadDano: int` — valor entre 1 y 3
- `nivel: int` — sube de 1 en 1 al dañar a un oponente, máximo 7

**Métodos:**
- `subirNivel(): void`
- `getCapacidadDano(): int`

---

## Capa Juego (`game`)

### Clase `CampoBatalla`
**Atributos:**
- `equipoA: Equipo`
- `equipoB: Equipo`
- `ancho: int`, `alto: int`
- `terminado: boolean`

**Métodos:**
- `crearEquipos(int tamano): void`
- `verificarGanador(): Equipo`
- `getDimensiones(): int[]`

### Clase `Equipo`
**Atributos:**
- `color: String`
- `simbolo: String`
- `mutantes: List<Mutante>`
- `vivos: int`, `muertos: int`

**Métodos:**
- `actualizarMarcador(): void`
- `estaEliminado(): boolean`

---

## Capa Control (`control`)

### Clase `ControladorMovimiento`
**Atributos:**
- `radioDeteccion: double`

**Métodos:**
- `moverMutante(Mutante m): void`
- `detectarColision(Mutante m1, Mutante m2): boolean`

### Clase `HiloCombate` (implementa `Runnable`)
**Atributos:**
- `mutanteA: Mutante`
- `mutanteB: Mutante`

**Métodos:**
- `run(): void`
- `resolverCombate(): void`

---

## Capa UI (`ui`)

### Clase `VentanaBatalla` (extiende `JFrame`)
**Métodos:**
- `actualizar(): void` — implementa Observer
- `dibujarMutantes(Graphics g): void`
- `mostrarGanador(Equipo ganador): void`
- `reiniciarPartida(): void`

### Interfaz `ObservadorBatalla`
**Métodos:**
- `actualizar(): void`

---

## Diagrama UML

```plantuml
@startuml MutantBattle

package model {
  class Mutante {
    - nombre: String
    - energia: int
    - capacidadDefensa: int
    - poder: PoderMutante
    - posicionX: double
    - posicionY: double
    - velocidad: double
    - vivo: boolean
    + mover(): void
    + atacar(objetivo: Mutante): void
    + defenderse(): void
    + recibirDano(dano: int): void
    + estaVivo(): boolean
    + getEnergia(): int
  }

  class PoderMutante {
    - nombre: String
    - capacidadDano: int
    - nivel: int
    + subirNivel(): void
    + getCapacidadDano(): int
  }

  Mutante "1" *-- "0..1" PoderMutante
}

package game {
  class CampoBatalla {
    - equipoA: Equipo
    - equipoB: Equipo
    - ancho: int
    - alto: int
    - terminado: boolean
    + crearEquipos(tamano: int): void
    + verificarGanador(): Equipo
    + getDimensiones(): int[]
  }

  class Equipo {
    - color: String
    - simbolo: String
    - mutantes: List<Mutante>
    - vivos: int
    - muertos: int
    + actualizarMarcador(): void
    + estaEliminado(): boolean
  }

  CampoBatalla "1" *-- "2" Equipo
  Equipo "1" o-- "3..11" Mutante
}

package control {
  class ControladorMovimiento {
    - radioDeteccion: double
    + moverMutante(m: Mutante): void
    + detectarColision(m1: Mutante, m2: Mutante): boolean
  }

  class HiloCombate {
    - mutanteA: Mutante
    - mutanteB: Mutante
    + run(): void
    + resolverCombate(): void
  }

  HiloCombate ..|> Runnable
  ControladorMovimiento ..> Mutante
  HiloCombate ..> Mutante
}

package ui {
  interface ObservadorBatalla {
    + actualizar(): void
  }

  class VentanaBatalla {
    + actualizar(): void
    + dibujarMutantes(g: Graphics): void
    + mostrarGanador(ganador: Equipo): void
    + reiniciarPartida(): void
  }

  VentanaBatalla ..|> ObservadorBatalla
  VentanaBatalla ..> CampoBatalla
}

@enduml
```
