package racetrack

class FooterTagLib {
    static defaultEncodeAs = 'html'
    static encodeAsForTags = [copyright: 'raw']
    
    def thisYear = {
      out << new Date().format('yyyy')
    }

    def copyright = { attrs, body ->
      out << "&copy; " << attrs.startYear << " - " << thisYear() << " " << body()
    }
}
