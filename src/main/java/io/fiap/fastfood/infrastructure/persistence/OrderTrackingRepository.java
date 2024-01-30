package io.fiap.fastfood.infrastructure.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderTrackingRepository extends ReactiveCrudRepository<OrderTrackingEntity, String> {
    Flux<OrderTrackingEntity> findByOrderIdOrderByOrderDateTime(String orderId);

    @Aggregation({"{" +
        "      '$group':{" +
        "         'start':{" +
        "            '$first':'$$ROOT'" +
        "         }," +
        "         'end':{" +
        "            '$last':'$$ROOT'" +
        "         }," +
        "         '_id':{" +
        "            '_id':'$id_pedido'" +
        "         }" +
        "      }" +
        "   }",
        "   {" +
        "      '$addFields':{" +
        "         'status':'$end.status'," +
        "         'id_pedido':'$end.id_pedido'," +
        "         'numero_pedido':'$end.numero_pedido'," +
        "         'data_hora':'$end.data_hora'," +
        "         'tempo_decorrido':{" +
        "            '$dateDiff':{" +
        "               'startDate':'$start.data_hora'," +
        "               'endDate':'$end.data_hora'," +
        "               'unit':'minute'" +
        "            }" +
        "         }" +
        "      }" +
        "   }",
        "   {" +
        "      '$project':{" +
        "         'status':'$status'," +
        "         'id_pedido':'$id_pedido'," +
        "         'numero_pedido':'$numero_pedido'," +
        "         'data_hora':'$data_hora'," +
        "         'tempo_decorrido':'$tempo_decorrido'" +
        "      }" +
        "   }",
        "   {" +
        "      '$sort':{" +
        "         'data_hora': 1 " +
        "      }" +
        "   }",
        "   {" +
        "      '$facet':{" +
        "         'metadata':[" +
        "            {" +
        "               '$count':'total'" +
        "            }," +
        "            {" +
        "               '$addFields':{" +
        "                  'page': :#{#page}" +
        "               }" +
        "            }" +
        "         ]," +
        "         'data':[" +
        "            {" +
        "               '$skip': :#{#skip}" +
        "            }," +
        "            {" +
        "               '$limit': :#{#limit}" +
        "            }" +
        "         ]" +
        "      }" +
        "   }"})
    Mono<PaginatedOrderTrackingEntity> findTracking(@Param("page") Integer page, @Param("skip") Integer skip, @Param("limit") Integer limit);
}
