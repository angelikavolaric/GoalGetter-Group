import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import { KarticeSeznam } from '../models/karticeSeznam';
import { Observable } from 'rxjs';

import { catchError } from 'rxjs/operators';
import { Kartica } from '../models/kartica';

@Injectable()
export class KarticeService {

  private headers = new HttpHeaders({'Content-Type': 'application/json'});
  private url = 'http://localhost:8080/v1/seznami';

  constructor(private http: HttpClient) {
  }

  getSeznami(): Observable<KarticeSeznam[]> {
    return this.http.get<KarticeSeznam[]>(this.url)
      .pipe(catchError(this.handleError));
  }

  getSeznam(id: number): Observable<KarticeSeznam> {
    const url = `${this.url}/${id}`;
    return this.http.get<KarticeSeznam>(url)
      .pipe(catchError(this.handleError));
  }

  delete(id: number): Observable<number> {
    const url = `${this.url}/${id}`;
    return this.http.delete<number>(url, {headers: this.headers})
      .pipe(catchError(this.handleError));
  }

  create(seznamId: number, artikel: Kartica): Observable<Kartica> {
    return this.http.post<Kartica>(this.url + '/' + seznamId + '/kartice', JSON.stringify(artikel), {headers: this.headers})
      .pipe(catchError(this.handleError));
  }

  private handleError(error: any): Promise<any> {
    console.error('Prišlo je do napake', error);
    return Promise.reject(error.message || error);
  }
}

