import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Uporabnik } from '../models/uporabnik';  // Assuming you have a Kartica model
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable()
export class UporabnikiService {

  private headers = new HttpHeaders({'Content-Type': 'application/json'});
  private url = 'http://localhost:8082/v1/uporabniki';

  constructor(private http: HttpClient) {}

  // Get all kartice
  getUporabniki(): Observable<Uporabnik[]> {
    return this.http.get<Uporabnik[]>(this.url)
      .pipe(catchError(this.handleError));
  }

  // Get a single kartica by ID
  getUporabnik(id: number): Observable<Uporabnik> {
    const url = `${this.url}/${id}`;
    return this.http.get<Uporabnik>(url)
      .pipe(catchError(this.handleError));
  }

  // Delete a kartica
  deleteUporabnik(id: number): Observable<number> {
    const url = `${this.url}/${id}`;
    return this.http.delete<number>(url, {headers: this.headers})
      .pipe(catchError(this.handleError));
  }

  // Create a new kartica
  createUporabnik(uporabnik: Uporabnik): Observable<Uporabnik> {
    return this.http.post<Uporabnik>(this.url, JSON.stringify(uporabnik), {headers: this.headers})
      .pipe(catchError(this.handleError));
  }

  // Update an existing kartica
  updateUporabnik(id: number, uporabnik: Uporabnik): Observable<Uporabnik> {
    if (uporabnik.id) {
      // Update the existing kartica if id exists
      const url = `${this.url}/${uporabnik.id}`;
      return this.http.put<Uporabnik>(url, JSON.stringify(uporabnik), {headers: this.headers})
        .pipe(catchError(this.handleError));
    } else {
      // Add a new kartica if id doesn't exist
      return this.http.post<Uporabnik>(this.url, JSON.stringify(uporabnik), {headers: this.headers})
        .pipe(catchError(this.handleError));
    }
  }

  // Handle any HTTP errors
  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }
}
