package racetrack

class RaceController {
    def scaffold = true

    def search() {
      flash.message = "Search results for ${params.q}"
      def hits = Race.search(params.q, params)
      respond hits.results, model: [raceInstanceCount: hits.total], view: 'index'
    }
}
