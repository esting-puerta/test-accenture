import { Injectable } from '@angular/core';
import { RemoteConfig, getValue } from '@angular/fire/remote-config';
import { Observable, from } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RemoteConfigService {
  constructor(private remoteConfig: RemoteConfig) {}

  // Obtener un valor booleano
  getBoolean(key: string): Observable<boolean> {
    return from(Promise.resolve(getValue(this.remoteConfig, key).asBoolean()));
  }

  // Obtener un valor numérico
  getNumber(key: string): Observable<number> {
    return from(Promise.resolve(getValue(this.remoteConfig, key).asNumber()));
  }

  // Obtener un valor de texto
  getString(key: string): Observable<string> {
    return from(Promise.resolve(getValue(this.remoteConfig, key).asString()));
  }

  // Obtener un valor JSON
  getJSON<T>(key: string): Observable<T> {
    return from(
      Promise.resolve(getValue(this.remoteConfig, key).asString())
        .then((str: string) => JSON.parse(str))
    );
  }
} 