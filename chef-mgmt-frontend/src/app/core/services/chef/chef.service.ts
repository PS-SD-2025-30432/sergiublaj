import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment.development';
import { ChefFilterDTO } from '../../../feature/chefs/models/chef-filter-dto.model';
import { ChefRequestDTO } from '../../../feature/chefs/models/chef-request-dto.model';
import { ChefResponseDTO } from '../../../feature/chefs/models/chef-response-dto.model';
import { CollectionResponseDTO } from '../../../shared/models/collection-response-dto.model';
import { ROUTES } from '../../config/routes.enum';
import { buildChefQueryParams } from '../../utils/rest-utils';


@Injectable({
  providedIn: 'root',
})
export class ChefService {

  constructor(private http: HttpClient) { }

  getAll(filter?: ChefFilterDTO): Observable<CollectionResponseDTO<ChefResponseDTO>> {
    return this.http.get<CollectionResponseDTO<ChefResponseDTO>>(
      `${environment.baseUrl}/v1/${ROUTES.CHEFS}`,
      { params: buildChefQueryParams(filter) },
    );
  }

  getById(chefId: string): Observable<ChefResponseDTO> {
    return this.http.get<ChefResponseDTO>(`${environment.baseUrl}/v1/${ROUTES.CHEFS}/${chefId}`);
  }

  save(chef: ChefRequestDTO): Observable<ChefResponseDTO> {
    return this.http.post<ChefResponseDTO>(`${environment.baseUrl}/v1/${ROUTES.CHEFS}`, chef);
  }

  update(chefId: string, chef: ChefRequestDTO): Observable<ChefResponseDTO> {
    return this.http.put<ChefResponseDTO>(`${environment.baseUrl}/v1/${ROUTES.CHEFS}/${chefId}`, chef);
  }

  delete(chefId: string): Observable<void> {
    return this.http.delete<void>(`${environment.baseUrl}/v1/${ROUTES.CHEFS}/${chefId}`);
  }
}
