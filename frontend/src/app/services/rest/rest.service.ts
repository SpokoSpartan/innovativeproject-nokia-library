import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError } from 'rxjs/internal/operators';
import { API_URL } from '../../config';
import { Observable, throwError } from 'rxjs/index';
import { MessageInfo } from '../../models/MessageInfo';

@Injectable({
	providedIn: 'root'
})
export class RestService {
	URL = API_URL + '/api/v1/';

	constructor(private http: HttpClient) {
	}

	async getAll(url: string) {
		return await this.http.get<MessageInfo>(this.URL + url, { withCredentials: true })
		.pipe(
			catchError(this.handleError)
		).toPromise();
	}

	save(url: string, item: any): Observable<any> {
		return this.http.post<any>(this.URL + url, item, { withCredentials: true })
		.pipe(
			catchError(this.handleError)
		);
	}

	post(url: string): Observable<any> {
		return this.http.post<any>(this.URL + url, null, { withCredentials: true })
		.pipe(
			catchError(this.handleError)
		);
	}

	update(url: string, id: number, item: any) {
		return this.http.post<any>(this.URL + url + '/update/' + id, item, { withCredentials: true })
		.pipe(
			catchError(this.handleError)
		);
	}

	remove(url: string, id: number) {
		return this.http.delete<any>(this.URL + url + id, { withCredentials: true })
		.pipe(
			catchError(this.handleError)
		);
	}

	changeSubscribeStatus(id: number) {
		return this.http.post<any>(this.URL + 'bookToOrder/subscribe/' + id, id, {withCredentials: true})
		.pipe(
			catchError(this.handleError)
		);
	}

	acceptBookToOrder(id: number) {
		return this.http.post<any>(this.URL + 'bookToOrder/accept/' + id, id, {withCredentials: true})
		.pipe(
			catchError(this.handleError)
		);
	}

	private handleError(error: HttpErrorResponse) {
		if (error.error instanceof ErrorEvent) {
			console.error('An error occurred:', error.error.message);
		} else {
			console.error(
				`Backend returned code ${error.status}, ` +
				`exception was: ${error.error.message}`);
		}
		return throwError(error);
	}
}
