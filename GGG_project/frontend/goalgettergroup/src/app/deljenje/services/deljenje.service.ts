import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Cilj } from '../models/cilj';  // Assuming you have a Kartica model
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable()
export class DeljenjeService {

  private headers = new HttpHeaders({'Content-Type': 'application/json'});
  private url = 'http://localhost:8087/v1/deljenje';  // Update the endpoint to work with 'kartice'


  constructor(private http: HttpClient) {}

  // Get all deljenje (sharing items)
  getDeljenja(): Observable<Cilj[]> {
    return this.http.get<Cilj[]>(this.url)
      .pipe(catchError(this.handleError));
  }

  // Get a single deljenje by ID
  getDeljenje(id: number): Observable<Cilj> {
    const url = `${this.url}/${id}`;
    return this.http.get<Cilj>(url)
      .pipe(catchError(this.handleError));
  }

  // Delete a deljenje by ID
  deleteDeljenje(id: number): Observable<number> {
    const url = `${this.url}/${id}`;
    return this.http.delete<number>(url, {headers: this.headers})
      .pipe(catchError(this.handleError));
  }

  // Create a new deljenje (sharing)
  createDeljenje(deljenje: Cilj): Observable<Cilj> {
    return this.http.post<Cilj>(this.url, JSON.stringify(deljenje), {headers: this.headers})
      .pipe(catchError(this.handleError));
  }

  // Update an existing deljenje (sharing)
  updateDeljenje(id: number, deljenje: Cilj): Observable<Cilj> {
    if (deljenje.id) {
      // Update the existing deljenje if id exists
      const url = `${this.url}/${deljenje.id}`;
      return this.http.put<Cilj>(url, JSON.stringify(deljenje), {headers: this.headers})
        .pipe(catchError(this.handleError));
    } else {
      // Add a new deljenje if id doesn't exist
      return this.http.post<Cilj>(this.url, JSON.stringify(deljenje), {headers: this.headers})
        .pipe(catchError(this.handleError));
    }
  }

  // Handle any HTTP errors
  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }
}
