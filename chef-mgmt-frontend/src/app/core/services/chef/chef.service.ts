import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ChefFilter } from '../../../feature/chefs/models/chef-filter.model';
import { ChefRequest } from '../../../feature/chefs/models/chef-request.model';
import { ChefResponse } from '../../../feature/chefs/models/chef-response.model';
import { CollectionResponseDTO } from '../../../shared/models/collection-response-dto.model';
import { ROUTES } from '../../config/routes.enum';
import { buildChefQueryParams } from '../../utils/rest-utils';


@Injectable({
  providedIn: 'root'
})
export class ChefService {

  constructor(private http: HttpClient) { }

  getAll(filter?: ChefFilter): Observable<CollectionResponseDTO<ChefResponse>> {
    return this.http.get<CollectionResponseDTO<ChefResponse>>(
      `/v1/${ROUTES.CHEFS}`,
      { params: buildChefQueryParams(filter) }
    );
  }

  getById(chefId: string): Observable<ChefResponse> {
    return this.http.get<ChefResponse>(`/v1/${ROUTES.CHEFS}/${chefId}`);
  }

  save(chef: ChefRequest): Observable<ChefResponse> {
    return this.http.post<ChefResponse>(`/v1/${ROUTES.CHEFS}`, chef);
  }

  update(chefId: string, chef: ChefRequest): Observable<ChefResponse> {
    return this.http.put<ChefResponse>(`/v1/${ROUTES.CHEFS}/${chefId}`, chef);
  }

  delete(chefId: string): Observable<void> {
    return this.http.delete<void>(`/v1/${ROUTES.CHEFS}/${chefId}`);
  }
}
