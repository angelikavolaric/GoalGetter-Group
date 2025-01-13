import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Casovnik } from '../models/casovnik';  // Import the Casovnik model
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable()
export class CasovnikService {

  private headers = new HttpHeaders({'Content-Type': 'application/json'});
  private url = 'http://localhost:8088/v1/timers';  // Update the endpoint for Casovniks

  constructor(private http: HttpClient) {}

  // Get all Casovniki
  getCasovniki(): Observable<Casovnik[]> {
    return this.http.get<Casovnik[]>(this.url)
      .pipe(catchError(this.handleError));
  }

  // Get a single Casovnik by ID
  getCasovnik(id: number): Observable<Casovnik> {
    const url = `${this.url}/${id}`;
    return this.http.get<Casovnik>(url)
      .pipe(catchError(this.handleError));
  }

  // Delete a Casovnik
  deleteCasovnik(id: number): Observable<void> {
    const url = `${this.url}/${id}`;
    return this.http.delete<void>(url, {headers: this.headers})
      .pipe(catchError(this.handleError));
  }

  // Create a new Casovnik
  createCasovnik(casovnik: Casovnik): Observable<Casovnik> {
    return this.http.post<Casovnik>(this.url, JSON.stringify(casovnik), {headers: this.headers})
      .pipe(catchError(this.handleError));
  }

  // Update an existing Casovnik
  updateCasovnik(id: number, casovnik: Casovnik): Observable<Casovnik> {
    const url = `${this.url}/${id}`;
    return this.http.put<Casovnik>(url, JSON.stringify(casovnik), {headers: this.headers})
      .pipe(catchError(this.handleError));
  }

  // Handle any HTTP errors
  private handleError(error: any): Observable<never> {
    console.error('An error occurred', error);
    throw new Error(error.message || error);  // Propagate the error
  }
}
